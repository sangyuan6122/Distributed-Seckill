package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemPrivMenu;
import com.jecp.sysmanage.model.TawSystemPrivMenuassign;
import com.jecp.sysmanage.vo.MenuAssignOwnerVO;

public interface TawSystemPrivMenuAssignDao extends BaseDao<TawSystemPrivMenuassign>{
	/**
	 * 获得未分配菜单方案
	 * @return
	 */
	public List<TawSystemPrivMenu> getUnAssign(String owner);
	/**
	 * 获得已分配菜单方案
	 * @return
	 */
	public List<TawSystemPrivMenu> getAssigned(String owner);
	/**
	 * 菜单方案是否已存在
	 * @param menuid
	 * @param owner
	 * @return
	 */
	public Boolean exist(String menuid,String owner);
	/**
	 * 删除已分配权限菜单
	 * @param menuid
	 * @param owner
	 */
	public void del(String menuid,String owner);
	/**
	 * 查询用户是否具有访问URL权限
	 * @param userid
	 * @param url
	 * @return
	 */
	public Boolean urlPermission(String userid,Long deptid,String url);
	/**
	 * 根据菜单方案获得所有已分配权限的OWNER
	 * @param menuid
	 * @return
	 */
	public List<MenuAssignOwnerVO> getByMenuid(String menuid);
	/**
	 * 根据菜单方案删除某个菜单权限
	 */
	public void delByMenuid(String menuid,String privid);
}
