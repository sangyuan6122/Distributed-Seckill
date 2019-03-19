package com.jecp.sysmanage.controller;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.common.util.StatusEnum;
import com.jecp.sysmanage.model.TawSystemDicttype;
import com.jecp.sysmanage.service.TawSystemDicttypeService;

@Controller
@Scope("prototype")
public class DictTypeController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemDicttypeService tawSystemDicttypeService;	
	/**
	 * 添加字典
	 * @return
	 */
	@RequestMapping("/dictadd")
	@ResponseBody
	public String add(TawSystemDicttype tawSystemDicttype){			
		if(tawSystemDicttypeService.isRepeat(tawSystemDicttype.getParentdictid(),tawSystemDicttype.getDictname(),"")>0){
			return StatusEnum.STATUS_REPEAT.toString();
		}else{
			tawSystemDicttypeService.save(tawSystemDicttype);
			return StatusEnum.STATUS_SUCCESS.toString();
		}			
	}
	/**
	 * 根据id删除数据字典
	 * @return
	 */
	@RequestMapping("/dictdel")
	@ResponseBody
	public String del(TawSystemDicttype tawSystemDicttype){
		int r=tawSystemDicttypeService.delDictTree(tawSystemDicttype.getDictid());	
		if(r==1) {
			return StatusEnum.STATUS_SUCCESS.toString();
		}else {
			return StatusEnum.STATUS_FAIL.toString();
		}
	}
	/**
	 * 获取单条数据字典对象填充表单
	 * @return 
	 */
	@RequestMapping("/dictget")
	@ResponseBody
	public String get(Integer dictid){		
		String json=tawSystemDicttypeService.get(dictid);
		return json;
	}
	/**
	 * 更新数据字典
	 * @return
	 */
	@RequestMapping("/dictupdate")
	@ResponseBody
	public String update(TawSystemDicttype tawSystemDicttype,String olddictname){		
		if(tawSystemDicttypeService.isRepeat(tawSystemDicttype.getParentdictid(), tawSystemDicttype.getDictname(),olddictname)>1){
			return StatusEnum.STATUS_REPEAT.toString();
		}else{
			tawSystemDicttypeService.update(tawSystemDicttype);
			return StatusEnum.STATUS_SUCCESS.toString();
		}	
	}
	/**
	 * 获得数据字典树，动态加载
	 * @return
	 */
	@RequestMapping("/dicttree")
	@ResponseBody
	public String tree(String parentdictid){
		String json=tawSystemDicttypeService.getTree(parentdictid);
		return json;
	}
	@RequestMapping("/dictgetToJson")
	@ResponseBody
	public String getToJson(String parentdictid){
		String json=tawSystemDicttypeService.getToJson(parentdictid);
		return json;		
	}
	@RequestMapping("/dictgetCodeToJson")
	@ResponseBody
	public String getCodeToJson(String parentdictid){
		String json=tawSystemDicttypeService.getCodeToJson(parentdictid);
		return json;	
	}
}
