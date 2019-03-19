package com.jecp.sysmanage.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.sysmanage.model.TawSystemPrivMenuassign;
import com.jecp.sysmanage.service.TawSystemPrivMenuAssignService;

@Controller
@Scope("prototype")
public class PrivMenuAssignController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivMenuAssignService tawSystemPrivMenuAssignService;
	/**
	 * 获得未分配权限
	 */
	@RequestMapping("/privassigngetunassign")
	@ResponseBody
	public String getunassign(String owner){
		String json=tawSystemPrivMenuAssignService.getunassign(owner);
		return json;	
	}
	/**
	 * 获得已分配权限 
	 * @return
	 */
	@RequestMapping("/privassigngetassigned")
	@ResponseBody
	public String getassigned(String owner){
		String json=tawSystemPrivMenuAssignService.getassigned(owner);
		return json;
	}
	/**
	 * 对象分配菜单权限
	 * @return
	 */
	@RequestMapping("/privassignadd")
	@ResponseBody
	public String add(TawSystemPrivMenuassign tawSystemPrivMenuassign){
		String json=tawSystemPrivMenuAssignService.add(tawSystemPrivMenuassign);			
		return json;	
	}
	/**
	 * 删除菜单方案中权限
	 */
	@RequestMapping("/privassigndel")
	@ResponseBody
	public String del(String menuid,String owner){		
		String json=tawSystemPrivMenuAssignService.del(menuid, owner);		
		return json;
	}
}
