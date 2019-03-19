package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemPriv;

public interface TawSystemPrivDao extends BaseDao<TawSystemPriv>{
	/**
	 * 根据父节点ID获得子节点
	 * @param pid
	 * @return
	 */
	public List<TawSystemPriv> getMenu(String userid,Long deptid,String pid,String showAll);
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
	
	
}
