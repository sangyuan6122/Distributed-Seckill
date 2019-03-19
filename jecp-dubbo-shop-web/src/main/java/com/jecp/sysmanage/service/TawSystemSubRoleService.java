package com.jecp.sysmanage.service;

import java.util.List;

import com.jecp.base.page.PageQuery;
import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemSubrole;

public interface TawSystemSubRoleService extends BaseService<TawSystemSubrole>{
	/**
	 * 根据roleid获得子角色列表
	 * @param roleid
	 * @param paginationQuery
	 * @return
	 */
	public String getByRoleid(String roleid,PageQuery paginationQuery);
	/**
	 * 批量添加子角色
	 * @param tawSystemSubrole
	 * @return
	 */
	public String adds(List<TawSystemSubrole> tawSystemSubrole);
	/**
	 * 根据用户ID获得子角色列表
	 * @param roleid
	 * @param userid
	 * @return
	 */
	public List<TawSystemSubrole> getByUser(String roleid,String userid);
}
