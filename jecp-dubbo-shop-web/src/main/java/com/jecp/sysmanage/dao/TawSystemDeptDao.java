package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemDept;

public interface TawSystemDeptDao extends BaseDao<TawSystemDept> {
	/**
	 * 根据父部门ID获得新加部门DEPTID
	 * @param parentdeptid
	 * @return
	 */
	public Long getNextDeptid(Long parentdeptid);
	/**
	 * 根据父部门ID获得新加RTX部门DEPTID
	 * @return
	 */
	public Long getNextRtxDeptid();
	/**
	 * 根据父部门获得
	 * @param parentdeptid
	 * @return
	 */
	public  List<TawSystemDept> getByParentdeptid(Long parentdeptid);
	/**
	 * 判断是否为含有子节点
	 * @param deptid
	 * @return
	 */
	public Boolean isHaveSub(Long deptid);
	/**
	 * 删除当前及其所有子部门
	 * @param deptid
	 * @return
	 */
	public void delSubAll(Long deptid);
}
