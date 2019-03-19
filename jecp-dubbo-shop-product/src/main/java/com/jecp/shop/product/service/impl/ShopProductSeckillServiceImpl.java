package com.jecp.shop.product.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.api.Compensable;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.common.cache.localcache.LocalCache;
import com.jecp.common.util.SnowflakeIdWorker;
import com.jecp.shop.order.api.ShopOrderHisApi;
import com.jecp.shop.order.dto.ShopOrderAddDTO;
import com.jecp.shop.order.enums.ShopOrderChannelEnum;
import com.jecp.shop.order.enums.ShopOrderSalesWayEnum;
import com.jecp.shop.product.cache.SecKillCache;
import com.jecp.shop.product.dao.ShopProductSeckillDao;
import com.jecp.shop.product.dto.SeckillDetailDTO;
import com.jecp.shop.product.dto.SeckillMqDTO;
import com.jecp.shop.product.enums.ProductStockControlEnum;
import com.jecp.shop.product.enums.SeckillKeyEnum;
import com.jecp.shop.product.enums.SeckillQueue;
import com.jecp.shop.product.enums.SeckillStrategyEnum;
import com.jecp.shop.product.exceptions.ShopProductBizException;
import com.jecp.shop.product.model.ShopProductSeckill;
import com.jecp.shop.product.strategy.SeckillFactory;
import com.jecp.shop.product.strategy.SeckillStrategy;
import com.jecp.trade.record.api.TradeRecordApi;

/**
 * @Title 秒杀业务功能
 * @author WWT
 * @date 2018年6月3日
 */
@Service 
public class ShopProductSeckillServiceImpl extends BaseServiceImpl<ShopProductSeckill> {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopProductSeckillServiceImpl shopProductSeckillService;
	@Autowired
	private ShopProductServiceImpl shopProductService;
	@Autowired
	private ShopProductSeckillDao shopProductSeckillDao;
	@Autowired
	private ShopOrderHisApi shopOrderHisApi;
	@Autowired
	private TradeRecordApi tradeRecordApi;
	private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1, 1);
	@Autowired
	@Qualifier("rabbitTemplateTransact")
	private RabbitTemplate rabbitTemplateTransact;
	@Autowired
	@Qualifier("rabbitTemplate")
	private RabbitTemplate rabbitTemplate;
	@PostConstruct
	private void init() {
		super.setBasedao(shopProductSeckillDao);
	}

	@Override
	public ShopProductSeckill getById(Serializable id) {		
		return super.getById(id);
	}
	
	/**
	 * 创建商品秒杀活动
	 * @param shopProductSeckill
	 * @return
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class)
	public String createSeckill(ShopProductSeckill shopProductSeckill) {
		int result = shopProductService.stockControl(shopProductSeckill.getProductId(),
				shopProductSeckill.getSeckillStock(), ProductStockControlEnum.REDUCE);
		if (result <= 0) {
			throw ShopProductBizException.LOW_STOCK;
		}
		SeckillStrategy seckillStrategy;
		//缓存秒杀库存		
		String seckillId = (String) super.save(shopProductSeckill);
		long effectiveMillisecond = shopProductSeckill.getEndTime().getTime() - System.currentTimeMillis();
		try {
			SeckillStrategyEnum seckillStrategyEnum=SeckillStrategyEnum.valueOf(shopProductSeckill.getCodeStrategy());
			seckillStrategy = SeckillFactory.getStrategy(seckillStrategyEnum);
			seckillStrategy.cacheStock(seckillId, shopProductSeckill.getSeckillStock(), (int) (effectiveMillisecond/1000));
		} catch (Exception e) {
			log.error("",e);
			throw ShopProductBizException.BULD_SECKILL_INSTANCE_ERROR;
		}
		//回收商品库存
		shopProductSeckillService.sendRecycleStockMsg(seckillId, String.valueOf(effectiveMillisecond) );
		//缓存秒杀商品到本地缓存
		SecKillCache.cacheShopProductSeckill(shopProductSeckill);
		return seckillId;
	}

	/**
	 * 秒杀列表
	 * 
	 * @return
	 */
	public List<SeckillDetailDTO> list() {		
		return shopProductSeckillDao.list();
	}

	/**
	 * 获得秒杀商品详情
	 * 
	 * @param seckillId
	 * @return
	 */
	public SeckillDetailDTO getSecKillDetailDTO(String seckillId) {
		String key = SeckillKeyEnum.SECKILL_DETAIL_DTO.join(seckillId);
		SeckillDetailDTO seckillDetailDTO = (SeckillDetailDTO) LocalCache.get(key);
		if (seckillDetailDTO == null) {
			seckillDetailDTO = shopProductSeckillDao.getSecKillDetailDTO(seckillId);
			LocalCache.put(key, seckillDetailDTO, seckillDetailDTO.getEndTime().getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
		}
		return seckillDetailDTO;
	}

	/**
	 * 减库存
	 * 
	 * @param seckillId
	 * @return
	 */
	public int reduceStock(String seckillId, Integer productCount) {
		return shopProductSeckillDao.reduceStock(seckillId, productCount);
	}

	/**
	 * 执行秒杀
	 * @param userId
	 * @param seckillId
	 */
	
	@Compensable(asyncConfirm = true, asyncCancel = true)
	@Transactional(rollbackFor = Exception.class)
	public void placeOrder(String userId, String seckillId) {
		long gtid = snowflakeIdWorker.nextId();
		SeckillDetailDTO secKillDetailDTO = getSecKillDetailDTO(seckillId);
		ShopOrderAddDTO shopOrderAddDTO = secKillDetailDTO.convertToShopOrderAddDTO(gtid, userId, 1L);
		shopOrderAddDTO.setSalesWay(ShopOrderSalesWayEnum.SECKILL.getCode());
		shopOrderAddDTO.setOrderChannel(ShopOrderChannelEnum.PC.getCode());
		shopOrderHisApi.createOrder(shopOrderAddDTO);
		tradeRecordApi.createTradeRecord(secKillDetailDTO.convertToTradeRecordInputDTO(gtid, userId));	
		int dbResult = shopProductSeckillService.reduceStock(seckillId, 1);
		if (dbResult <= 0) {
			log.info("秒杀商品ID:{}", seckillId);
			throw ShopProductBizException.LOW_STOCK;
		}		
	}
	
	/**
	 * 预减库存
	 * @param userId
	 * @param seckillId
	 * @return
	 */
	public boolean preStockReduction(String userId, String seckillId) {
		SeckillStrategy seckillStrategy;
		SeckillStrategyEnum seckillStrategyEnum;
		ShopProductSeckill shopProductSeckill;		
		/*是否重复秒杀*/
		if(SecKillCache.checkOrderRepeat(seckillId, userId)) {
			return false;
		}
		/*获取秒杀商品*/
		shopProductSeckill=SecKillCache.getShopProductSeckill(seckillId);
		if(shopProductSeckill==null) {
			shopProductSeckill=shopProductSeckillService.getById(seckillId);			
			SecKillCache.cacheShopProductSeckill(shopProductSeckill);
		}		
		/*执行对应代码策略*/
		try {
			seckillStrategyEnum=SeckillStrategyEnum.valueOf(shopProductSeckill.getCodeStrategy());
			seckillStrategy = SeckillFactory.getStrategy(seckillStrategyEnum);
		} catch (Exception e) {
			log.error("",e);
			throw ShopProductBizException.BULD_SECKILL_INSTANCE_ERROR;
		}
		/*获取资格*/
		boolean seckillResult = seckillStrategy.seckill(seckillId, userId);
		int expireTime=(int) TimeUnit.MILLISECONDS.toSeconds(shopProductSeckill.getEndTime().getTime() - System.currentTimeMillis() );
		if (!seckillResult) {
			seckillStrategy.cacheStockStatus(seckillId, expireTime);
		} else {
			SecKillCache.cacheSuccessUser(seckillId, userId, expireTime);
			SeckillMqDTO seckillMqDTO = new SeckillMqDTO(userId, seckillId);
			rabbitTemplate.convertAndSend(SeckillQueue.SECKILL_QUEUE, JSON.toJSONString(seckillMqDTO));
			return true;
		}
		return false;
	}

	/**
	 * 发送库存回收消息
	 * 已开启事务(类似弱XA)，rabbitmq增强发送可靠性
	 * 如需完全可靠则需用可靠消息服务或XA事务
	 * @param seckillId
	 * @param effectiveMillisecond
	 */			
	public void sendRecycleStockMsg(String seckillId, String effectiveMillisecond) {
		rabbitTemplateTransact.convertAndSend(SeckillQueue.RECYCLE_STOCK_DELAY_QUEUE, (Object)seckillId, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setExpiration(effectiveMillisecond);
				return message;
			}
		});
	}
	/**
	 * 库存回收
	 * @param seckillId
	 */
	@Transactional(rollbackFor = Exception.class)
	public void recyleStock(String seckillId) {
		ShopProductSeckill shopProductSeckill=super.getById(seckillId);
		int seckillStock=shopProductSeckill.getSeckillStock();
		if(seckillStock>0) {
			int result=shopProductSeckillDao.reduceStock(seckillId, seckillStock);
			if(result>0) {
				shopProductService.stockControl(shopProductSeckill.getProductId(), seckillStock, ProductStockControlEnum.INCREASE);
			}
		}
	}
	

	
}
