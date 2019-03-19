package com.jecp.sysmanage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.common.util.StatusEnum;
import com.jecp.sysmanage.model.TawSystemSubroleuser;
import com.jecp.sysmanage.service.TawSystemSubRoleUserService;

public class SubRoleUserController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemSubRoleUserService tawSystemSubRoleUserService;
	
	/**
	 * 获得子角色人员
	 * @return
	 */
	@RequestMapping("/usubroleget")
	@ResponseBody
	public String get(String subroleid){
		String json=tawSystemSubRoleUserService.getBySubroleid(subroleid);		
		return json;
	}
	/**
	 * 添加子角色人员
	 * @return
	 */
	@RequestMapping("/usubroleadds")
	@ResponseBody
	public String adds(@RequestParam(value="subroles") List<TawSystemSubroleuser> subroleusers){
		String json=tawSystemSubRoleUserService.adds(subroleusers);					
		return json;		
	}
	/**
	 * 根据ID删除
	 * @return
	 */
	@RequestMapping("/usubroledel")
	@ResponseBody
	public String del(String id){
		tawSystemSubRoleUserService.delete(id);		
		return StatusEnum.STATUS_SUCCESS.toString();		
	}
	/**
	 * 获得人员角色树
	 * @return
	 */
	@RequestMapping("/usubroletree")
	@ResponseBody
	public String tree(String roleid){		
		String json=tawSystemSubRoleUserService.getRoleUserTree(roleid);		
		return json;
	}
}
