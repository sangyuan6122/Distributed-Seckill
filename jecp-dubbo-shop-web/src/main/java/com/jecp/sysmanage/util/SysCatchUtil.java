package com.jecp.sysmanage.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.jecp.sysmanage.dao.TawSystemDeptDao;
import com.jecp.sysmanage.dao.TawSystemDicttypeDao;
import com.jecp.sysmanage.dao.TawSystemUserDao;
import com.jecp.sysmanage.model.TawSystemDept;
import com.jecp.sysmanage.model.TawSystemDicttype;
import com.jecp.sysmanage.model.TawSystemUser;

@Component
public class SysCatchUtil{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	private static Map<Long,TawSystemDept> deptCache=new HashMap<Long,TawSystemDept>();
	private static Map<String,TawSystemUser> userCache=new HashMap<String,TawSystemUser>();
	private static Set<String> superAdminCache=new HashSet<String>();
	private static Map<String,TawSystemDicttype> dictCache=new HashMap<String,TawSystemDicttype>();
	@Resource
	private TawSystemDeptDao tawSystemDeptDao;
	@Resource
	private TawSystemUserDao tawSystemUserDao;
	@Resource
	private TawSystemDicttypeDao tawSystemDicttypeDao;

	/**
	 * 加载缓存数据
	 */
	@PostConstruct
	public void loadCache() {
		log.info("********加载缓存信息********");
		List<TawSystemDept> listdept=tawSystemDeptDao.findAll();
		for(TawSystemDept dept:listdept){
			deptCache.put(dept.getDeptid(), dept);
		}
		List<TawSystemUser> listuser=tawSystemUserDao.findAll();
		for(TawSystemUser user:listuser){
			userCache.put(user.getUserid(), user);
			if("admin".equals(user.getRemark())) {
				superAdminCache.add(user.getUserid());
			}
		}
		List<TawSystemDicttype> listdict=tawSystemDicttypeDao.findAll();
		for(TawSystemDicttype dict:listdict){
			dictCache.put(dict.getDictid().toString(), dict);
		}		
		log.info("*******加载缓存结束*******");
	}
	/**
	 * 是否超级管理员
	 * @param userId
	 * @return
	 */
	public static boolean superAdmin(String userId) {
		return superAdminCache.contains(userId);
	}
	/**
	 * 更新用户缓存
	 * @param user
	 */
	public synchronized static void updateUserCache(TawSystemUser user){
		userCache.put(user.getUserid(), user);
	}
	/**
	 * 更新部门缓存
	 * @param dept
	 */
	public synchronized static void updateDeptCache(TawSystemDept dept){
		deptCache.put(dept.getDeptid(), dept);
	}
	/**
	 * 更新字典缓存
	 * @param dict
	 */
	public synchronized static void updateDictCache(TawSystemDicttype dict){
		dictCache.put(dict.getDictid().toString(), dict);
	}
	/**
	 * 删除用户缓存
	 * @param user
	 */
	public synchronized static void delUserCache(String userid){
		userCache.remove(userid);
	}
	/**
	 * 删除部门缓存
	 * @param dept
	 */
	public synchronized static void delDeptCache(String deptid){
		deptCache.remove(deptid);
	}
	/**
	 * 删除字典缓存
	 * @param dict
	 */
	public synchronized static void delDictCache(String dictid){
		dictCache.remove(dictid);
	}
	/**
	 * userid转换为对象
	 * @param userid
	 * @return
	 */
	public static TawSystemUser useridToUser(String userid){
		return SysCatchUtil.userCache.get(userid);
	}
	/**
	 * 部门字典转换
	 * @param deptid
	 * @return
	 */
	public static String deptidToDeptName(Long deptid){		
		return deptCache.get(deptid)==null?"":deptCache.get(deptid).getDeptname();
	}
	/**
	 * 部门全称字典转换
	 * @param deptid
	 * @return
	 */
	public static String deptidToDeptFullName(Long deptid){		
		return deptCache.get(deptid)==null?"":deptCache.get(deptid).getDeptfullname();
	}
	/**
	 * 系统字典转换
	 * @param dictid
	 * @return
	 */
	public static String dictidToDictName(String dictid){		
		return dictCache.get(dictid)==null?"":dictCache.get(dictid).getDictname();
	}
	/**
	 * 用户ID转名称
	 * @param userid
	 * @return
	 */
	public static String useridToUserName(String userid){		
		return userCache.get(userid)==null?"":userCache.get(userid).getUsername();
	}
	/**
	 * 用户ID转部门名称
	 * @param userid
	 * @return
	 */
	public static String useridToDeptName(String userid){		
		return userCache.get(userid)==null?"":userCache.get(userid).getDeptname();
	}
	/**
	 * 用户ID转部门ID
	 * @param userid
	 * @return
	 */
	public static String useridToDeptId(String userid){		
		return userCache.get(userid)==null?"":String.valueOf(userCache.get(userid).getDeptid());
	}
	/**
	 * 用户ID转手机号码
	 * @param userid
	 * @return
	 */
	public static String useridToMobile(String userid){		
		return userCache.get(userid)==null?"":userCache.get(userid).getMobile();
	}
	/**
	 * 返回当前所有UserCache
	 * @return
	 */
	public static Map<String,TawSystemUser> getUserCache(){
		return userCache;
	}
	/**
	 * 返回当前所有deptCache
	 * @return
	 */
	public static Map<Long,TawSystemDept> getDeptCache(){
		return deptCache;
	}

	public static String getDictidByDictCode(String dictcode,String remark) {
		String dictid="";
		for(Map.Entry<String, TawSystemDicttype> entry:dictCache.entrySet()) {
			TawSystemDicttype tawSystemDicttype=entry.getValue();
			if(tawSystemDicttype.getRemark()==null||tawSystemDicttype.getDictcode()==null) {
				continue;
			}else if(tawSystemDicttype.getRemark().equals(remark)
					&&tawSystemDicttype.getDictcode().equals(dictcode)){
				dictid=entry.getKey();
				break;
			}
		}
		return dictid;
	}

}
