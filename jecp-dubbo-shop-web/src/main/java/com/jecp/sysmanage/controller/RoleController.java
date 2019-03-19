package com.jecp.sysmanage.controller;


import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jecp.base.controller.BaseController;
import com.jecp.common.util.StatusEnum;
import com.jecp.sysmanage.model.TawSystemRole;
import com.jecp.sysmanage.service.TawSystemRoleService;

@Controller
@Scope("prototype")
public class RoleController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemRoleService tawSystemRoleService;
	/**
	 * 获得大角色树
	 * @return
	 */
	@RequestMapping("/roletree")
	@ResponseBody
	public String tree(){
		String result = tawSystemRoleService.getTree();					
		return result;
	}
	/**
	 * 添加角色
	 * @return
	 */
	@RequestMapping("/roleadd")
	@ResponseBody
	public String add(TawSystemRole tawSystemRole){
		tawSystemRole.setDeleted("0");
		tawSystemRoleService.save(tawSystemRole);		
		return StatusEnum.STATUS_SUCCESS.toString();
	}
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping("/roledel")
	@ResponseBody
	public String del(String roleid){		 
		tawSystemRoleService.del(roleid);
		return StatusEnum.STATUS_SUCCESS.toString();
	}
	/**
	 * 角色更新
	 * @return
	 */
	@RequestMapping("/roleupdate")
	@ResponseBody
	public String update(TawSystemRole tawSystemRole){				 
		tawSystemRole.setDeleted("0");
		tawSystemRoleService.update(tawSystemRole);
		return StatusEnum.STATUS_SUCCESS.toString();
	}
	/**
	 * 获得单条记录
	 * @return
	 */
	@RequestMapping("/roleget")
	@ResponseBody
	public String get(String roleid){	
		TawSystemRole tawSystemRole =tawSystemRoleService.getById(roleid);
		String result=JSONObject.toJSONString(tawSystemRole);		
		return result;
	}
}
