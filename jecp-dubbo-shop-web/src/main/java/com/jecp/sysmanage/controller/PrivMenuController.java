package com.jecp.sysmanage.controller;

import java.io.File;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jecp.base.controller.BaseController;
import com.jecp.sysmanage.model.TawSystemPrivMenu;
import com.jecp.sysmanage.service.TawSystemPrivMenuService;

@Controller
@Scope("prototype")
public class PrivMenuController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);	
	@Resource
	private TawSystemPrivMenuService tawSystemPrivMenuService;		
	/**
	 * 图片上传
	 */
	@RequestMapping("/privmenuupload")
	public String upload(){	
		super.getRequest().setAttribute("savePath", "upload"+File.separator+"sysmanage"+File.separator+"ico");
		return "forward:attachupload";
	}
	/**
	 * 加载全部菜单方案 
	 * @return
	 */
	@RequestMapping("/privmenuload")
	@ResponseBody
	public String load(){
		String json=tawSystemPrivMenuService.load(super.getPageQuery().getPage(),super.getPageQuery().getRows());
		return json;	
	}
	/**
	 * 添加菜单方案项
	 * @return
	 */
	@RequestMapping("/privmenuadd")
	@ResponseBody
	public String add(TawSystemPrivMenu tawSystemPrivMenu){
		String json=tawSystemPrivMenuService.add(tawSystemPrivMenu);		
		return json;		
	}
	/**
	 * 删除菜单方案项及关联菜单集合
	 * @return
	 */
	@RequestMapping("/privmenudel")
	@ResponseBody
	public String del(String id){
		String json=tawSystemPrivMenuService.del(id);		
		return json;
	}
	/**
	 * 获得菜单方案单条记录
	 * @return
	 */
	@RequestMapping("/privmenuget")
	@ResponseBody
	public String get(String id){
		String json=tawSystemPrivMenuService.get(id);
		return json;
	}
	/**
	 * 更新菜单方案
	 * @return
	 */
	@RequestMapping("/privmenuupdate")
	@ResponseBody
	public String update(TawSystemPrivMenu tawSystemPrivMenu){
		JSONObject jSONObject=new JSONObject();
		tawSystemPrivMenuService.update(tawSystemPrivMenu);
		jSONObject.put("status", "1");
		return 	jSONObject.toJSONString();
	}

}
