package com.jecp.sysmanage.service;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemPrivMenuitem;

public interface TawSystemPrivMenuitemService extends BaseService<TawSystemPrivMenuitem>{
	/**
	 * 根据MENUID获得菜单集合
	 * @param menuid
	 * @param clientType
	 * @return
	 */
	public String getTree(String menuid,String clientType);
	/**
	 * 添加单条菜单方案集合
	 * @param tawSystemPrivMenuitem
	 * @return
	 */
	public String add(TawSystemPrivMenuitem tawSystemPrivMenuitem);
	/**
	 * 递归删除菜单方案集合
	 * @param menuid
	 * @param privid
	 * @return
	 */
	public String delAllTree(String menuid,String privid);
}
