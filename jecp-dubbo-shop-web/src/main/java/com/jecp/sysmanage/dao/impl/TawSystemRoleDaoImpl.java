package com.jecp.sysmanage.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemRoleDao;
import com.jecp.sysmanage.model.TawSystemRole;
@Repository(value="tawSystemRoleDao")
public class TawSystemRoleDaoImpl extends BaseDaoImpl<TawSystemRole> implements TawSystemRoleDao{

	@Override
	public List<TawSystemRole> getTree() {
		Query query=getSession().createQuery("from TawSystemRole where deleted=0 order by sort");
		List<TawSystemRole> list = query.list();
		
		return list;
	}

	@Override
	public Boolean isHaveSub(String roleid) {
		Query query=getSession().createQuery("from TawSystemRole where parentid=?");
		query.setParameter(0, roleid);
		if(query.list().size()>0){
			return true;
		}
		return false;
	}

	@Override
	public int del(String roleid) {		
		Query query=getSession().createQuery("update TawSystemRole set deleted=1 where id=?");
		query.setParameter(0, roleid);
		int result = query.executeUpdate();
		return result;
	}

}
