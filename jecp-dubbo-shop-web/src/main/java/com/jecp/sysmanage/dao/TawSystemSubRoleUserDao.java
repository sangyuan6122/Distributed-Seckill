package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.common.model.Tree;
import com.jecp.sysmanage.model.TawSystemSubroleuser;

public interface TawSystemSubRoleUserDao extends BaseDao<TawSystemSubroleuser>{
	/**
	 * 根据子角色ID获得人员
	 * @param subroleid
	 * @return
	 */
	public List<TawSystemSubroleuser> getBySubroleid(String subroleid);
	/**
	 * 角色人员重复
	 * @param featurecodes
	 * @return
	 */
	public List<TawSystemSubroleuser> isExist(String[] featurecodes);
	/**
	 * 根据角色ID获得角色让人员树
	 * @param roleid
	 * @return
	 */
	public List<Tree> getRoleUserTree(String roleid);
}
