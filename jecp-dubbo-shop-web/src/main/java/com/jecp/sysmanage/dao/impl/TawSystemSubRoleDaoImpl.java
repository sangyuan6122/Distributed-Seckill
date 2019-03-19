package com.jecp.sysmanage.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemSubRoleDao;
import com.jecp.sysmanage.model.TawSystemSubrole;
@Repository(value="tawSystemSubRoleDao")
public class TawSystemSubRoleDaoImpl extends BaseDaoImpl<TawSystemSubrole> implements TawSystemSubRoleDao {

	@Override
	public List<TawSystemSubrole> getByRoleid(String roleid,Integer beginIndex, Integer size, String[] sort, String[] order) {
		//多字段排序
		String sortSql=" order by ";
		if(sort!=null&&order!=null){
			for(int i=0;i<sort.length;i++){
				sortSql=sortSql+sort[i]+" "+order[i]+",";
			}
		}
		sortSql=sortSql+"id";
		Query q = getSession().createQuery("from TawSystemSubrole where roleid=?");
		q.setParameter(0, roleid);
		if(beginIndex!=null&&size!=null){
			q.setFirstResult((beginIndex-1)*size).setMaxResults(size);
		}
		List<TawSystemSubrole> list = q.list();
		return list;
	}
	@Override
	public Long getByRoleidCount(String roleid) {
		Query q = getSession().createQuery("select count(*) from TawSystemSubrole where roleid=?");
		q.setParameter(0, roleid);
		return (Long) q.uniqueResult();
	}
	@Override
	public List<TawSystemSubrole> exists(String[] featurecode) {
		Query q = getSession().createQuery("from TawSystemSubrole where featurecode in(:featurecode)");
		q.setParameterList("featurecode",featurecode);
		List<TawSystemSubrole> list = q.list();
		
		return list;
	}

	@Override
	public List<TawSystemSubrole> getByUser(String roleid, String userid) {
		Query q = getSession().createNativeQuery("select s.id,s.roleid,s.deptid,s.deptname,s.subrolename,s.featurecode,s.remark "
				+ "from taw_system_subroleuser u,taw_system_subrole s where u.subroleid=s.id and s.roleid=? and u.userid=?")
				.addEntity(TawSystemSubrole.class);
		q.setParameter(1, roleid);
		q.setParameter(2, userid);
		return q.list();
	}

	


}
