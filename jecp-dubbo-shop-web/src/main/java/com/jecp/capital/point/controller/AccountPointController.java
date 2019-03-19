package com.jecp.capital.point.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.base.page.PageBean;
import com.jecp.capital.point.api.AccountPointApi;
import com.jecp.capital.point.dto.AccountPointListDTO;

/**
 * @Title 用户积分账户
 * @author WWT
 * @date 2018年10月25日
 */
@Controller
@RequestMapping("/capital/point")
public class AccountPointController extends BaseController {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	public AccountPointApi accountPointApi;

	/**
	 * 列表
	 * @param status
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(String status) {
		PageBean<AccountPointListDTO> pageBean = accountPointApi.list(super.getPageQuery());
		return pageBean.toDataGridJSON();
	}

}
