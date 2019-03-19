package com.jecp.sysmanage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.common.util.StatusEnum;
import com.jecp.sysmanage.model.TawSystemSubrole;
import com.jecp.sysmanage.service.TawSystemSubRoleService;

@Controller
@Scope("prototype")
public class SubRoleController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemSubRoleService tawSystemSubRoleService;

	/**
	 * 根据roleid获得列表
	 * @return
	 */
	@RequestMapping("/subrolegetByRoleId")
	@ResponseBody
	public String getByRoleId(String roleid){
		String json = tawSystemSubRoleService.getByRoleid(roleid, super.getPageQuery());		
		return json;
	}
	/**
	 * 批量添加子角色
	 * @return
	 */
	@RequestMapping("/subroleadd")
	@ResponseBody
	public String add(@RequestParam(value="subroles") List<TawSystemSubrole> subroles){
		String json=tawSystemSubRoleService.adds(subroles);		
		return json;
	}
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping("/subroledel")
	@ResponseBody
	public String del(@RequestParam(value="ids") String[] ids){
		tawSystemSubRoleService.deletes(ids);		
		return StatusEnum.STATUS_SUCCESS.toString();
	}
}
