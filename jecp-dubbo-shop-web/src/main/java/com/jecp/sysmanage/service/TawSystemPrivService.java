package com.jecp.sysmanage.service;

import java.util.List;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemPriv;

public interface TawSystemPrivService extends BaseService<TawSystemPriv>{

	/**
	 * 获得菜单
	 * @param basePath
	 * @param userid
	 * @param deptid
	 * @param pid
	 * @param showAll
	 * @return
	 */
	public String getMenu(String basePath,String userid,Long deptid,String pid,String showAll);
	/**
	 * 删除当前父节点
	 * @param pid
	 */
	public void delTree(String pid);

	/**
	 * 根据taw_system_priv_menuitem表中menuid获得菜单对象
	 * @param menuid
	 * @return
	 */
	public List<TawSystemPriv> getByMenuId(String menuid);
	/**
	 * 获取单条菜单对象填充表单
	 * @return 
	 */
	public String getTree(String pid);
}
