package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.common.model.Tree;
import com.jecp.sysmanage.model.TawSystemPrivMenuitem;

public interface TawSystemPrivMenuitemDao extends BaseDao<TawSystemPrivMenuitem>{
	/**
	 * 根据菜单方案ID获得菜单权限集合
	 * @param menuid
	 * @return
	 */
	public List<Tree> getTree(String menuid,String clientType);
	/**
	 * 递归删除菜单及所有子菜单
	 * @param menuid
	 * @param privid
	 */
	public void delAllTree(String menuid,String privid);
	/**
	 * 判断是否存在父节点
	 * @param menuid
	 * @param privid
	 * @return
	 */
	public boolean isHaveParent(String menuid,String privid);
	/**
	 * 插入根节点数据
	 * @param menuid
	 * @param clientType(C:电脑端，M:移动端)
	 */
	public void addRootTree(String menuid,String clientType);
	/**
	 * 根据菜单方案ID删除
	 * @param menuid
	 */
	public void delByMenuId(String menuid);

}
