package com.jecp.sysmanage.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemUserDao;
import com.jecp.sysmanage.model.TawSystemDept;
import com.jecp.sysmanage.model.TawSystemUser;
import com.jecp.sysmanage.util.RSAUtil;
import com.jecp.sysmanage.util.SysCatchUtil;

@Repository("tawSystemUserDao")
public class TawSystemUserDaoImpl extends BaseDaoImpl<TawSystemUser> implements TawSystemUserDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Override
	public TawSystemUser login(String userid, String password) {	
		try{			
								
			Query q = getSession().createQuery("from TawSystemUser where userid=?");
			q.setParameter(0, userid);
			List<TawSystemUser> ulist = q.list();			
			if(ulist.size()>0){ 				
				TawSystemUser user=ulist.get(0);			
				if(password.equals(RSAUtil.decode1(user.getPassword())) ){
					return user;
				}				
			}			
		}catch(Exception e){
			e.printStackTrace();
			log.error("",e);
			return null;
		}
		return null;
	}

	public Boolean isHaveDept(Long deptid) {
		Map<Long,TawSystemDept> depts=SysCatchUtil.getDeptCache();
		for(Long did:depts.keySet()){
			if(deptid.equals(depts.get(did).getParentdeptid())){
				return true;
			}
		}		
//		Query query=getSession().createQuery("from TawSystemDept where parentdeptid=?");
//		query.setParameter(0, deptid);
//		if(query.list().size()>0){
//			return true;
//		}
//		query=getSession().createQuery("from TawSystemUser where deptid=?");
//		query.setParameter(0, deptid);
//		if(query.list().size()>0){
//			return true;
//		}
		return false;
	}
	public Boolean isHaveUser(String deptid) {
		Map<String,TawSystemUser> users=SysCatchUtil.getUserCache();
		for(String userid:users.keySet()){
			if(deptid.equals(users.get(userid).getDeptid())){
				return true;
			}
		}
		return false;
	}
	@Override
	public TawSystemUser getByUserid(String userid) {
		Query q = getSession().createQuery("from TawSystemUser where userid=?");
		q.setParameter(0, userid);
		List<TawSystemUser> ulist = q.list();
		TawSystemUser user ;
		if(ulist.size()>0){ 
			user=ulist.get(0);
			return user;
		}else{
			return null;
		}	
	}
	@Override
	public List<String> getMobilesByUserids(String[] userids) {
		List<String> mobiles=new ArrayList<String>();
		if(userids!=null&&userids.length>0){
			Query q= getSession().createQuery("from TawSystemUser where userid IN (:userids)")
					.setParameterList("userids", userids);
			List<TawSystemUser> ulist = q.list();
			for(int i=0;i<ulist.size();i++){
				if(ulist.get(i).getMobile()==null||"".equals(ulist.get(i).getMobile())){
					continue;
				}else{
					String[] str=ulist.get(i).getMobile().split(",");
					for(String mobile:str){
						mobiles.add(mobile);
					}
				}				
			}
		}
		
		return mobiles;
	}
	@Override
	public List<String> getMobilesByDeptids(Long[] deptids) {
		List<String> mobiles=new ArrayList<String>();
		if(deptids!=null&&deptids.length>0){
			Query q= getSession().createQuery("from TawSystemUser where deptid IN (:deptids)")
					.setParameterList("deptids", deptids);
			List<TawSystemUser> ulist = q.list();
			for(int i=0;i<ulist.size();i++){
				if(ulist.get(i).getMobile()==null||"".equals(ulist.get(i).getMobile())){
					continue;
				}else{
					String[] str=ulist.get(i).getMobile().split(",");
					for(String mobile:str){
						mobiles.add(mobile);
					}
				}				
			}
		}
		
		return mobiles;
	}
	@Override
	public List<TawSystemUser> getByDeptid(Long[] deptids) {
		List<TawSystemUser> ulist=new ArrayList<TawSystemUser>();;
		if(deptids!=null&&deptids.length>0){
			Query q= getSession().createQuery("from TawSystemUser where deptid IN (:deptids)")
					.setParameterList("deptids", deptids);
			ulist= q.list();
		} 
		return ulist;
	}
	@Override
	public TawSystemUser getByWeixinId(String weixinid) {
		if(weixinid !=null&&!"".equals(weixinid)){
			Query q= getSession().createQuery("from TawSystemUser where weixinid =:weixinid")
					.setParameter("weixinid", weixinid);
			return (TawSystemUser) q.uniqueResult();
		}
		return null;
	}
}
