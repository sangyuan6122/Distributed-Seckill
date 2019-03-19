package com.jecp.sysmanage.controller;
 
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jecp.base.controller.BaseController;
import com.jecp.sysmanage.model.TawSystemDept;
import com.jecp.sysmanage.service.TawSystemDeptService;

@Controller
@Scope("prototype")
public class DeptController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemDeptService tawSystemDeptService;
	/**
	 * 增加部门
	 * @return
	 */
	@RequestMapping("/deptadd")
	@ResponseBody
	public String add(TawSystemDept tawSystemDept){		
		String json=tawSystemDeptService.addDept(tawSystemDept);
		return json;	
	}
	/**
	 * 删除部门
	 * @return
	 */
	@RequestMapping("/deptdel")
	@ResponseBody
	public String del(Long deptid){		
		String json=tawSystemDeptService.delDept(deptid);	
		return json;			
	}
	/**
	 * 获得单条部门树对象
	 * @return
	 */
	@RequestMapping("/deptget")
	@ResponseBody
	public String get(Long deptid){		
		String json=tawSystemDeptService.getDeptJson(deptid);
		return json;	
	}
	/**
	 * 部门信息更新
	 * @return
	 */
	@RequestMapping("/deptupdate")
	@ResponseBody
	public String update(TawSystemDept tawSystemDept){
		String json=tawSystemDeptService.modify(tawSystemDept);
		return json;	
	}
	/**
	 * 获得部门树
	 * @return
	 */
	@RequestMapping("/depttree")
	@ResponseBody
	public String tree(Long parentdeptid){					
		String json = tawSystemDeptService.getTree(parentdeptid,"dept");			
		return json;
	}

}
