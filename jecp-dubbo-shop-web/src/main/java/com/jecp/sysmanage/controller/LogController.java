package com.jecp.sysmanage.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.sysmanage.model.TawSystemLog;
import com.jecp.sysmanage.service.TawSystemLogService;
import com.jecp.sysmanage.vo.TawSystemLogQueryVO;

@Controller
@Scope("prototype")
public class LogController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemLogService tawSystemLogService;
	private String begintime;
	private String endtime;
	
	@RequestMapping("/slogload")
	@ResponseBody
	public String load(TawSystemLog tawSystemLog){
		TawSystemLogQueryVO vo=new TawSystemLogQueryVO(begintime, endtime, tawSystemLog.getModule(),
				tawSystemLog.getBusinessid(), tawSystemLog.getUserid(), tawSystemLog.getType());
		String json=tawSystemLogService.query(vo, super.getPageQuery());			
		return json;
	}
}
