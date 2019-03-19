package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemRole;

public interface TawSystemRoleDao extends BaseDao<TawSystemRole>{
	/**
	 * 获得角色菜单树
	 * @return
	 */
	public List<TawSystemRole> getTree();
	/**
	 * 判断是否为含有子节点
	 * @param roleid
	 * @return
	 */
	public Boolean isHaveSub(String roleid);
	/**
	 * 删除角色
	 * @param roleid
	 */
	public int del(String roleid);
}
