package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemDept;

public interface TawSystemDeptService extends BaseService<TawSystemDept>{
	/**
	 * 添加部门
	 * @param tawSystemDept
	 * @return
	 */
	public String addDept(TawSystemDept tawSystemDept);
	/**
	 * 删除部门
	 * @param deptid
	 * @return
	 */
	public String delDept(Long deptid);
	/**
	 * 获得部门JSON对象
	 * @param deptid
	 * @return
	 */
	public String getDeptJson(Long deptid);
	/**
	 * 获得部门树
	 * @param parentDeptid
	 * @param treeType
	 * @return
	 */
	public String getTree(Long parentDeptid,String treeType);
	/**
	 * 更新
	 * @param tawSystemDept
	 * @return
	 */
	public String modify(TawSystemDept tawSystemDept);
}
