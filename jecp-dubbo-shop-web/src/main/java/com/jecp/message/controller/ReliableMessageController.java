package com.jecp.message.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.base.page.PageBean;
import com.jecp.base.result.BaseCodeMsgEnum;
import com.jecp.base.result.BaseResult;
import com.jecp.message.api.ReliableMessageApi;
import com.jecp.message.dto.MessageStatusStatisticsDTO;
import com.jecp.message.dto.ReliableMessageListDTO;

/**
 * @Title 可靠消息服务
 * @author WWT
 * @date 2018年10月25日
 */
@Controller
@RequestMapping("/message")
public class ReliableMessageController extends BaseController {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Autowired
	public ReliableMessageApi reliableMessageApi;
	
	/**
	 * 列表
	 * @param status
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(String status) {
		PageBean<ReliableMessageListDTO> pageBean = reliableMessageApi.list(status, super.getPageQuery());
		return pageBean.toDataGridJSON();
	}
	/**
	 * 状态统计
	 * @param status
	 * @return
	 */
	@RequestMapping("/getStatusStatistics")
	@ResponseBody
	public MessageStatusStatisticsDTO getStatusStatistics() {
		MessageStatusStatisticsDTO messageStatusStatisticsDTO = reliableMessageApi.getStatusStatistics();
		return messageStatusStatisticsDTO;
	}
	/**
	 * 异常消息重新处理
	 * @return
	 */
	@RequestMapping("/exceptionHandling")
	@ResponseBody
	public BaseResult exceptionHandling(@RequestBody String[] ids) {
		reliableMessageApi.exceptionHandling(ids);
		return BaseResult.of(BaseCodeMsgEnum.SUCCESS);
	}
}
