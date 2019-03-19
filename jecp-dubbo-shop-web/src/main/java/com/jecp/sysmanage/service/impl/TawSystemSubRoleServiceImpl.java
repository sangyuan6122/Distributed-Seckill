package com.jecp.sysmanage.service.impl;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.page.PageQuery;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemSubRoleDao;
import com.jecp.sysmanage.model.TawSystemSubrole;
import com.jecp.sysmanage.service.TawSystemSubRoleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("tawSystemSubRoleService")
public class TawSystemSubRoleServiceImpl extends BaseServiceImpl<TawSystemSubrole> implements TawSystemSubRoleService {
	private TawSystemSubRoleDao tawSystemSubRoleDao;
	@Override
	public String getByRoleid(String roleid,PageQuery paginationQuery) {
		JSONObject jSONObject=new JSONObject();
		Long count=tawSystemSubRoleDao.getByRoleidCount(roleid);		
		List<TawSystemSubrole> list=tawSystemSubRoleDao.getByRoleid(roleid,paginationQuery.getPage(), paginationQuery.getRows(),paginationQuery.getSort(),paginationQuery.getOrder());
		if(list!=null&&list.size()>0){
			jSONObject.accumulate("total", count);
			jSONObject.accumulate("rows", JSONArray.fromObject(list));
			return jSONObject.toString();
		}
		
		return "[]";
	}
	@Transactional("txManager")
	@Override
	public String adds(List<TawSystemSubrole> subroles) {
		JSONObject jSONObject=new JSONObject();		
		String[] featurecode =new String[subroles.size()];//特征码集合
		String key;
		TawSystemSubrole subrole;
		Iterator<TawSystemSubrole> itr=subroles.iterator();
		int i=0;
		while(itr.hasNext()){
			subrole=itr.next();			
			key=subrole.getRoleid()+subrole.getDeptid();//特征码规则
			key=Base64.getEncoder().encodeToString(key.getBytes());
			subrole.setFeaturecode(key );
			featurecode[i]=key;
			i++;
		}
		List<TawSystemSubrole> list=tawSystemSubRoleDao.exists(featurecode);
		
		if(list.size()>0){
			jSONObject.accumulate("status", "2");
			JSONArray jSONArray=new JSONArray();
			for(TawSystemSubrole s:list){
				jSONArray.add(s.getSubrolename());
			}
			jSONObject.accumulate("msg", jSONArray);
		}else{
			for(TawSystemSubrole sbr:subroles){			
				tawSystemSubRoleDao.save(sbr);
			}
			jSONObject.accumulate("status", "1");
		}
		return jSONObject.toString();
	}
	@Override
	public List<TawSystemSubrole> getByUser(String roleid, String userid) {
		
		return tawSystemSubRoleDao.getByUser(roleid, userid);
	}	
	/*set、get*/
	@Override
	@Resource(name="tawSystemSubRoleDao")
	public void setBasedao(BaseDao<TawSystemSubrole> basedao) {
		super.setBasedao(basedao);		
	}

}
