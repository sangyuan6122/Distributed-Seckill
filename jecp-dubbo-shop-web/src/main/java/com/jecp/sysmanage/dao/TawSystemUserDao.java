package com.jecp.sysmanage.dao;

import java.util.List;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemUser;

public interface TawSystemUserDao extends BaseDao<TawSystemUser>{
	/**
	 * 系统登陆
	 * @param userid
	 * @param password
	 * @return
	 */
	public TawSystemUser login(String userid,String password);

	/**
	 * 根据人员ID查询的到对象信息
	 * @param userid
	 * @return
	 */
	public TawSystemUser getByUserid(String userid);
	/**
	 * 根据人员ID查询手机号码
	 * @param userids
	 * @return
	 */
	public List<String> getMobilesByUserids(String[] userids);
	/**
	 * 根据部门ID查询手机号码
	 * @param userids
	 * @return
	 */
	public List<String> getMobilesByDeptids(Long[] deptids);
	/**
	 * 根据多个部门id查询用户列表
	 * @param deptids
	 * @return
	 */
	public List<TawSystemUser> getByDeptid(Long[] deptids);
	/**
	 * 根据微信ID查询
	 * @param weixinid
	 * @return
	 */
	public TawSystemUser getByWeixinId(String weixinid);
}
