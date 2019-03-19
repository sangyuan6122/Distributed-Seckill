package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemSubrole;

public interface TawSystemSubRoleDao extends BaseDao<TawSystemSubrole>{
	/**
	 * 根据roleid获得子角色列表
	 * @param roleid
	 * @return
	 */
	public List<TawSystemSubrole> getByRoleid(String roleid,Integer beginIndex, Integer size, String[] sort, String[] order);
	/**
	 * 根据roleid获得子角色总数
	 * @param roleid
	 * @return
	 */
	public Long getByRoleidCount(String roleid);
	/**
	 * 是否存在
	 * @param featurecode
	 * @return
	 */
	public List<TawSystemSubrole> exists(String[] featurecode);	
	/**
	 * 根据用户ID获得子角色列表
	 * @param roleid
	 * @param userid
	 * @return
	 */
	public List<TawSystemSubrole> getByUser(String roleid,String userid);
}
