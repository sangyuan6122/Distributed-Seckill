package com.jecp.sysmanage.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemPrivMenuAssignDao;
import com.jecp.sysmanage.model.TawSystemPrivMenu;
import com.jecp.sysmanage.model.TawSystemPrivMenuassign;
import com.jecp.sysmanage.vo.MenuAssignOwnerVO;
@Repository(value="tawSystemPrivMenuAssignDao")
public class TawSystemPrivMenuAssignDaoImpl extends BaseDaoImpl<TawSystemPrivMenuassign> implements TawSystemPrivMenuAssignDao {

	@Override
	public List<TawSystemPrivMenu> getUnAssign(String owner) {
		Query query=getSession().createNativeQuery("select m.* from taw_system_priv_menu m "
				+ "where not exists(select menuid from taw_system_priv_menuassign a "
				+ "where owner=:owner and m.id=a.menuid)").addEntity(TawSystemPrivMenu.class);
		query.setParameter("owner", owner);
		List<TawSystemPrivMenu> list = query.list();
		
		return list;
	}

	@Override
	public List<TawSystemPrivMenu> getAssigned(String owner) {
		Query query=getSession().createNativeQuery("select m.* from taw_system_priv_menu m "
				+ "where exists(select menuid from taw_system_priv_menuassign a "
				+ "where owner=:owner and m.id=a.menuid)").addEntity(TawSystemPrivMenu.class);
		query.setParameter("owner", owner);
		List<TawSystemPrivMenu> list = query.list();
		return list;
	}

	@Override
	public Boolean exist(String menuid, String owner) {
		Query query=getSession().createQuery("from TawSystemPrivMenuassign where menuid=:menuid and owner=:owner");
		query.setParameter("menuid", menuid);
		query.setParameter("owner", owner);
		List<TawSystemPrivMenuassign> list = query.list();
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void del(String menuid, String owner) {
		Query query=getSession().createQuery("delete from TawSystemPrivMenuassign where menuid=:menuid and owner=:owner");
		query.setParameter("menuid", menuid);
		query.setParameter("owner", owner);
		query.executeUpdate();
	}

	@Override
	public Boolean urlPermission(String userid,Long deptid, String url) {
		if("admin".equals(userid)){
			return true;
		}
		Query query=getSession().createNativeQuery("select * from taw_system_priv_menuassign where url=:url and owner in(:userid,:deptid)");
		query.setParameter("url", url);
		query.setParameter("userid", userid);
		query.setParameter("deptid", String.valueOf(deptid));
		List list = query.list();
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public List<MenuAssignOwnerVO> getByMenuid(String menuid) {
		Query query=getSession().createNativeQuery("select distinct menuid as \"menuid\",owner as \"owner\",type as \"type\" from taw_system_priv_menuassign where menuid=:menuid").setResultTransformer(Transformers.aliasToBean(MenuAssignOwnerVO.class));
		query.setParameter("menuid", menuid);	
		
		return query.list();
	}
	@Override
	public void delByMenuid(String menuid, String privid) {
		Query query=getSession().createQuery("delete from TawSystemPrivMenuassign where menuid=:menuid"
				+ " and privid=:privid");
		query.setParameter("menuid", menuid);
		query.setParameter("privid", privid);
		query.executeUpdate();		
	}
	
	
}
