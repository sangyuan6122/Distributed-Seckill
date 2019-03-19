package com.jecp.sysmanage.service;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemUser;

public interface TawSystemUserService extends BaseService<TawSystemUser>{
	/**
	 * 系统登陆
	 * @param response
	 * @param userid
	 * @param password
	 * @param request
	 * @return
	 */
	public String login(HttpServletResponse response,String userid,String password,HttpServletRequest request);
	/**
	 * 用户鉴权获得Session
	 * @param response
	 * @param userid
	 * @param password
	 * @param request
	 * @return
	 */
	public String authorize(HttpServletResponse response,String userid,String password,HttpServletRequest request); 
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public String addUser(TawSystemUser user);
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public String delUser(String id);
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public int updateUser(TawSystemUser user);
	/**
	 * 根据部门ID查询
	 * @param deptid
	 * @return
	 */
	public List<TawSystemUser> getByDeptid(Long[] deptids);
	/**
	 * 获得部门人员树
	 * @param deptid
	 * @return
	 */
	public String getDeptUserTree(Long deptid);
	/**
	 * 获得对象
	 * @param id
	 * @return
	 */
	public String get(String id);
	/**
	 * 生成所有人员COOKIE
	 * @return
	 */
	public String allUserCookie(HttpServletRequest request);
	
}
