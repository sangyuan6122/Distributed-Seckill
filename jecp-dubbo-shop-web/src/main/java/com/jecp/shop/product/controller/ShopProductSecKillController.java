package com.jecp.shop.product.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.base.result.BaseCodeMsgEnum;
import com.jecp.base.result.BaseResult;
import com.jecp.common.util.TimeUtil;
import com.jecp.shop.product.api.ShopProductSecKillApi;
import com.jecp.shop.product.dto.SeckillAddDTO;
import com.jecp.shop.product.dto.SeckillDetailDTO;

/**
 * @Title 秒杀服务
 * @author WWT
 * @date 2018年6月18日
 */
@Controller
@RequestMapping("/shop/secKill")
public class ShopProductSecKillController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	private ShopProductSecKillApi shopProductSecKillApi;

	@RequestMapping("/add")
	@ResponseBody
	public BaseResult create(SeckillAddDTO seckillAddDTO) {
		shopProductSecKillApi.createSecKill(seckillAddDTO);
		return BaseResult.of(BaseCodeMsgEnum.SUCCESS);
	}

	@RequestMapping("/list")
	@ResponseBody
	public List<SeckillDetailDTO> list() {
		List<SeckillDetailDTO> list = shopProductSecKillApi.list();
		return list;
	}

	@RequestMapping("/secKill")
	@ResponseBody
	public BaseResult secKill(String seckillId) {
		boolean result = shopProductSecKillApi.secKill(super.getUserId(), seckillId);
		if (result) {
			return BaseResult.of(BaseCodeMsgEnum.SUCCESS);
		} else {
			return BaseResult.of(BaseCodeMsgEnum.FAILED);
		}
	}

	@RequestMapping("/serverTime")
	@ResponseBody
	public String serverTime() {
		return TimeUtil.getcuTime();
	}

}
