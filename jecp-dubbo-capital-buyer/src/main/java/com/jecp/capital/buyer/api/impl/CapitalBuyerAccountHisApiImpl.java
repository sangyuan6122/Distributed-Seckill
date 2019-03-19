package com.jecp.capital.buyer.api.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.capital.buyer.api.CapitalBuyerAccountHisApi;
import com.jecp.capital.buyer.dto.BuyerAccountHisInputDTO;
import com.jecp.capital.buyer.enums.AccountHisStatusEnum;
import com.jecp.capital.buyer.exceptions.CapitalBuyerBizException;
import com.jecp.capital.buyer.model.CapitalAccountHis;
import com.jecp.capital.buyer.service.impl.BuyerAccountHisServiceImpl;
import com.jecp.capital.buyer.service.impl.BuyerAccountServiceImpl;

/**
 * @Title 买家资金账户服务
 * @author WWT
 * @date 2018年8月3日
 */
@Service
public class CapitalBuyerAccountHisApiImpl implements CapitalBuyerAccountHisApi {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);		
	@Autowired
	private BuyerAccountServiceImpl capitalAccountService;
	@Autowired
	private BuyerAccountHisServiceImpl capitalAccountHisService;

	/**
	 * 买家账户资金转入
	 */
	@Override
	@Compensable(confirmMethod = "confirmCredit", cancelMethod = "cancelCredit", transactionContextEditor = DubboTransactionContextEditor.class)
	@Transactional
	public void credit(BuyerAccountHisInputDTO capitalAccountHisInputDTO) {
		
	}

	@Transactional
	public void confirmCredit(BuyerAccountHisInputDTO capitalAccountHisInputDTO) {

	}

	@Transactional
	public void cancelCredit(BuyerAccountHisInputDTO capitalAccountHisInputDTO) {

	}

	/**
	 * 买家账户资金转出
	 */
	@Override
	@Compensable(confirmMethod = "confirmDebit", cancelMethod = "cancelDebit", transactionContextEditor = DubboTransactionContextEditor.class)
	@Transactional
	public void debit(BuyerAccountHisInputDTO buyerAccountHisInputDTO) {
		log.debug("============>调用买家资金转出Try接口,接收参数:{}",buyerAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService
				.getByGtid(buyerAccountHisInputDTO.getGtid());

		if (foundCapitalAccountHis == null) {
			CapitalAccountHis capitalAccountHis = buyerAccountHisInputDTO.convertToBuyerAccountHis();
			/*悲观锁实现,数据库for update耗时长，改用行锁耗时短*/			
			/*CapitalAccount capitalAccount = capitalAccountService.getByUserid(buyerAccountHisInputDTO.getUserId(),true);
			capitalAccountHis.trying(capitalAccount.getBalance());
			capitalAccountHisService.save(capitalAccountHis);
			capitalAccount.debitTry(buyerAccountHisInputDTO.getTradeAmount());			
			capitalAccountService.update(capitalAccount);*/
	
			/*数据库行锁实现*/
			capitalAccountHis.trying(null);
			capitalAccountHisService.save(capitalAccountHis);
			int result=capitalAccountService.tryDebit(buyerAccountHisInputDTO.getUserId(), buyerAccountHisInputDTO.getTradeAmount());
			if(result!=1) {
				log.error("接收参数:{}",buyerAccountHisInputDTO.toString());
				throw CapitalBuyerBizException.ACCOUNT_NOT_FOUND;
			}
		}
	}

	@Transactional
	public void confirmDebit(BuyerAccountHisInputDTO buyerAccountHisInputDTO) {
		log.debug("============>转账Confirm开始:{}",buyerAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService.getByGtid(buyerAccountHisInputDTO.getGtid());
		log.info("查询历史记录:{}",foundCapitalAccountHis.toString());
		if (foundCapitalAccountHis != null
				&& AccountHisStatusEnum.TRYING.name().equals(foundCapitalAccountHis.getStatus())) {
			foundCapitalAccountHis.confirm();
			capitalAccountHisService.update(foundCapitalAccountHis);
			/*悲观锁for update*/
			/*CapitalAccount capitalAccount = capitalAccountService.getByUserid(buyerAccountHisInputDTO.getUserId(),true);
			capitalAccount.debitConfirm(buyerAccountHisInputDTO.getTradeAmount());			
			capitalAccountService.update(capitalAccount);*/
			/*数据库行锁*/
			capitalAccountService.confirmDebit(buyerAccountHisInputDTO.getUserId(), buyerAccountHisInputDTO.getTradeAmount());
		}		
	}

	@Transactional
	public void cancelDebit(BuyerAccountHisInputDTO buyerAccountHisInputDTO) {
		log.debug("============>转账Cance开始:{}",buyerAccountHisInputDTO.toString());
		CapitalAccountHis foundCapitalAccountHis = capitalAccountHisService.getByGtid(buyerAccountHisInputDTO.getGtid());
		if (foundCapitalAccountHis != null
				&& AccountHisStatusEnum.TRYING.name().equals(foundCapitalAccountHis.getStatus())) {
			foundCapitalAccountHis.cancel();
			capitalAccountHisService.update(foundCapitalAccountHis);
			/*悲观锁for update*/
			/*CapitalAccount capitalAccount = capitalAccountService.getByUserid(buyerAccountHisInputDTO.getUserId(),true);
			capitalAccount.debitCancel(buyerAccountHisInputDTO.getTradeAmount());
			capitalAccountService.save(capitalAccount);*/
			/*数据库行锁*/
			capitalAccountService.cancelDebit(buyerAccountHisInputDTO.getUserId(), buyerAccountHisInputDTO.getTradeAmount());
		}		
	}
}
