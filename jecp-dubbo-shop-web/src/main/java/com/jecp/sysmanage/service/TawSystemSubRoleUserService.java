package com.jecp.sysmanage.service;

import java.util.List;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemSubroleuser;

public interface TawSystemSubRoleUserService  extends BaseService<TawSystemSubroleuser>{
	/**
	 * 根据子角色ID获得人员
	 * @param subroleid
	 * @return
	 */
	public String getBySubroleid(String subroleid);
	/**
	 * 根据角色ID获得角色让人员树
	 * @param roleid
	 * @return
	 */
	public String getRoleUserTree(String roleid);
	/**
	 * 批量添加角色人员
	 * @param subroleusers
	 * @return
	 */
	public String adds(List<TawSystemSubroleuser> subroleusers);
}
