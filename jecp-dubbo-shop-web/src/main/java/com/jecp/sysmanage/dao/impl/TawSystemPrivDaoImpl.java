package com.jecp.sysmanage.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemPrivDao;
import com.jecp.sysmanage.model.TawSystemPriv;
import com.jecp.sysmanage.util.SysCatchUtil;

@Repository(value="tawSystemPrivDao")
public class TawSystemPrivDaoImpl extends BaseDaoImpl<TawSystemPriv> implements TawSystemPrivDao {

	@Override
	public List<TawSystemPriv> getMenu(String userid,Long deptid,String pid,String showAll) {
		String sql="";
		Query q;
		if(SysCatchUtil.superAdmin(userid)){
			if("".equals(showAll)||showAll==null||showAll.equals("0")){				
				sql="from TawSystemPriv where hide=0 and menulevel<>'3' and parentprivid=? order by sort";					
			}else{
				sql="from TawSystemPriv where parentprivid=? order by to_number(sort)";
			}
			q = getSession().createQuery(sql);
			q.setParameter(0,pid);
			
		}else{
			if("".equals(showAll)||showAll==null||showAll.equals("0")){				
				sql="from TawSystemPriv as tawSystemPriv where tawSystemPriv.hide=0 and tawSystemPriv.menulevel<>'3' and tawSystemPriv.parentprivid=:pid and exists (select 1 from TawSystemPrivMenuassign as a where a.privid=tawSystemPriv.privid and a.owner in(:userid,:deptid)) order by sort";						
				q = getSession().createQuery(sql);
				q.setParameter("pid",pid);
				q.setParameter("userid",userid);
				q.setParameter("deptid",String.valueOf(deptid));
			}else{
				sql="from TawSystemPriv where parentprivid=? order by to_number(sort)";
				q = getSession().createQuery(sql);
				q.setParameter(0,pid);				
			}						
		}
		List<TawSystemPriv> plist = q.list();
		return plist;
	}

	@Override
	public void delTree(String pid) {	
		Query q =getSession().createQuery("delete from TawSystemPriv where privid='"+pid+"' or parentprivid='"+pid+"'");
		q.executeUpdate();
	}

	@Override
	public List<TawSystemPriv> getByMenuId(String menuid) {
		Query query=getSession().createNativeQuery("select p.* from taw_system_priv_menuitem i,"
				+ "taw_system_priv p where i.menuid=:menuid and p.privid=i.privid").addEntity(TawSystemPriv.class);
		query.setParameter("menuid", menuid);
		List<TawSystemPriv> list = query.list();
		return list;
	}



}
