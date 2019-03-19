package com.jecp.sysmanage.dao.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemDeptDao;
import com.jecp.sysmanage.model.TawSystemDept;
@Repository(value="tawSystemDeptDao")
public class TawSystemDeptDaoImpl extends BaseDaoImpl<TawSystemDept> implements TawSystemDeptDao {

	@Override
	public Long getNextDeptid(Long parentdeptid) {
		Query query=getSession().createQuery("select max(deptid) from TawSystemDept where parentdeptid=?");
		query.setParameter(0, parentdeptid);
		Long maxdeptid=-999l ;
		if(query.uniqueResult() !=null){
			maxdeptid=((Long)query.uniqueResult()).longValue()+1;
		}else{
			maxdeptid=new Long(parentdeptid)*100;
		} 
		return maxdeptid.longValue();
	}
	@Override
	public Long getNextRtxDeptid() { 
		Query query=getSession().createQuery("select max(rtxdeptid) from TawSystemDept");

		Long maxrtxdeptid=1l;
		if(query.uniqueResult() !=null){
			maxrtxdeptid=(Long)query.uniqueResult()+1;
			
		} 
		return maxrtxdeptid;
	}
	@Override
	public List<TawSystemDept> getByParentdeptid(Long parentdeptid) {
		StringBuffer sqlwhere= new StringBuffer();
		sqlwhere.append(" where 1=1");
		List<Serializable> params=new ArrayList<>();
		if(parentdeptid!=null){
			sqlwhere.append(" and parentdeptid=?");
			params.add(parentdeptid);
		}
		Query query=getSession().createQuery("from TawSystemDept "+sqlwhere+" order by seq");
		for(int i=0;i<params.size();i++) {
			query.setParameter(i, params.get(i));
		}
		List<TawSystemDept> dlist = query.list();        
		return dlist;
	}
	@Override
	public Boolean isHaveSub(Long deptid) {
		Query query=getSession().createQuery("from TawSystemDept where parentdeptid=?");
		query.setParameter(0, deptid);
		if(query.list().size()>0){
			return true;
		}
		return false;
	}
	@Override
	public void delSubAll(Long deptid) {
		Query query=getSession().createNativeQuery("delete from taw_system_dept where deptid in(" + 
				"select deptid from taw_system_dept start with deptid=:deptid connect by prior deptid=parentdeptid)");
		query.setParameter("deptid", deptid);		
		query.executeUpdate();	
	}

}
