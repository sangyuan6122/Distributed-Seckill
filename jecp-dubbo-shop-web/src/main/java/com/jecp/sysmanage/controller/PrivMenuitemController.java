package com.jecp.sysmanage.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.sysmanage.model.TawSystemPrivMenuitem;
import com.jecp.sysmanage.service.TawSystemPrivMenuitemService;

@Controller
@Scope("prototype")
public class PrivMenuitemController extends BaseController {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivMenuitemService tawSystemPrivMenuitemService;

	/**
	 * 根据MENUID获得菜单集合
	 * 
	 * @return
	 */
	@RequestMapping("/privitemget")
	@ResponseBody
	public String get(String menuid, String clienttype) {
		String json = tawSystemPrivMenuitemService.getTree(menuid, clienttype);
		return json;
	}

	/**
	 * 添加单条菜单方案集合
	 * 
	 * @return
	 */
	@RequestMapping("/privitemadd")
	@ResponseBody
	public String add(TawSystemPrivMenuitem tawSystemPrivMenuitem) {
		String json = tawSystemPrivMenuitemService.add(tawSystemPrivMenuitem);
		return json;
	}

	/**
	 * 递归删除菜单方案集合
	 * 
	 * @return
	 */
	@RequestMapping("/privitemdel")
	@ResponseBody
	public String del(String menuid, String privid) {
		String json = tawSystemPrivMenuitemService.delAllTree(menuid, privid);
		return json;
	}
}
