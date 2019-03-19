package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemPrivMenuassign;

public interface TawSystemPrivMenuAssignService extends BaseService<TawSystemPrivMenuassign>{
	/**
	 * 获得未分配权限 
	 * @param owner
	 * @return
	 */
	public String getunassign(String owner);
	/**
	 * 获得已分配权限 
	 * @param owner
	 * @return
	 */
	public String getassigned(String owner);
	/**
	 * 对象分配菜单权限
	 * @param tawSystemPrivMenuassign
	 * @return
	 */
	public String add(TawSystemPrivMenuassign tawSystemPrivMenuassign);
	/**
	 * 删除菜单方案中权限
	 * @param menuid
	 * @param owner
	 * @return
	 */
	public String del(String menuid,String owner);
}
