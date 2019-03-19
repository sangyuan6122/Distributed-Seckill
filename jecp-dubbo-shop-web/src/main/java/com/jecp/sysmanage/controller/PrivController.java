package com.jecp.sysmanage.controller;

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
import com.jecp.sysmanage.model.TawSystemPriv;
import com.jecp.sysmanage.model.TawSystemUser;
import com.jecp.sysmanage.service.TawSystemPrivService;
import com.jecp.sysmanage.util.SysCatchUtil;

@Controller
@Scope("prototype")
public class PrivController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivService tawSystemPrivService;
	/**
	 * 获得菜单树，动态加载json
	 * @return
	 */
	@RequestMapping("/prvmenuTree")
	@ResponseBody
	public String menuTree(@RequestParam(value="pid") String pid,@RequestParam(value="showAll",required=false) String showAll){
		String basePath=super.getRequest().getScheme()+"://"+super.getRequest().getServerName()+":"+
				super.getRequest().getServerPort()+super.getRequest().getContextPath();
//		TawSystemUser tawSystemUser=(TawSystemUser) super.getRequest().getAttribute("tawSystemUser");

		TawSystemUser tawSystemUser=SysCatchUtil.useridToUser(super.getUserId());
		String json=tawSystemPrivService.getMenu(basePath,tawSystemUser.getUserid(),
				tawSystemUser.getDeptid(), pid, showAll);
		return json; 		
	}
	/**
	 * 添加菜单
	 * @return
	 */
	@RequestMapping("/prvaddTree")
	@ResponseBody
	public String addTree(TawSystemPriv tawSystemPriv){
		tawSystemPrivService.save(tawSystemPriv);
		return StatusEnum.STATUS_SUCCESS.toString();				
	}
	/**
	 * 删除菜单
	 * @return
	 */
	@RequestMapping("/prvdelTree")
	@ResponseBody
	public String delTree(@RequestParam(value="pid") String pid){				
		tawSystemPrivService.delTree(pid);		
		return StatusEnum.STATUS_SUCCESS.toString();
	}
	/**
	 * 获取单条菜单对象填充表单
	 * @return 
	 */
	@RequestMapping("/prvgetTree")
	@ResponseBody
	public String getTree(@RequestParam(value="pid") String pid){
		String json=tawSystemPrivService.getTree(pid);
		return json;
	}
	/**
	 * 更新菜单树
	 * @return
	 */
	@RequestMapping("/prvupdateTree")
	@ResponseBody
	public String updateTree(TawSystemPriv tawSystemPriv){		
		tawSystemPrivService.update(tawSystemPriv);
		return StatusEnum.STATUS_SUCCESS.toString();
	}

}
