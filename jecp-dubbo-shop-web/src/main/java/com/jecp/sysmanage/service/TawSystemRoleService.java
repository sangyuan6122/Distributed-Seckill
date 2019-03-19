package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemRole;

public interface TawSystemRoleService extends BaseService<TawSystemRole>{
	/**
	 * 获得角色菜单树
	 * @return
	 */
	public String getTree();
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
