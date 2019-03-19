package com.jecp.sysmanage.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.sysmanage.dao.TawSystemDicttypeDao;
import com.jecp.sysmanage.model.TawSystemDicttype;


@Repository(value="tawSystemDicttypeDao")
public class TawSystemDicttypeDaoImpl extends BaseDaoImpl<TawSystemDicttype> implements TawSystemDicttypeDao {

	@Override
	public List<TawSystemDicttype> getDictTree(String pid) {
		String sql="from TawSystemDicttype where parentdictid=? order by sort";		
		Query q = getSession().createQuery(sql);
		q.setParameter(0,pid);
		List<TawSystemDicttype> plist = q.list();
		return plist;
	}

	@Override
	public void delDictTree(Integer dictid) {
		Query q =getSession().createQuery("delete from TawSystemDicttype where dictid="+dictid+" or parentdictid='"+dictid+"'");
		q.executeUpdate();		
	}

	@Override
	public int isRepeat(String parentdictid,String newdictname,String olddiatname) {
		Query q = getSession().createQuery("from TawSystemDicttype where parentdictid=? and (dictname=? or dictname=?) ");
		q.setParameter(0,parentdictid);
		q.setParameter(1,newdictname);
		q.setParameter(2,olddiatname);
		List<TawSystemDicttype> plist = q.list();
		if(plist.size()>0){
			return plist.size();
		}
		return 0;
	}

	


}
