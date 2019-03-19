package com.jecp.sysmanage.service.impl;

import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemSubRoleUserDao;
import com.jecp.sysmanage.model.TawSystemSubroleuser;
import com.jecp.sysmanage.service.TawSystemSubRoleUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("tawSystemSubRoleUserService")
public class TawSystemSubRoleUserServiceImpl extends BaseServiceImpl<TawSystemSubroleuser> implements TawSystemSubRoleUserService{
	private TawSystemSubRoleUserDao tawSystemSubRoleUserDao;
	@Override
	public String getBySubroleid(String subroleid) {		 
		List<TawSystemSubroleuser> list = tawSystemSubRoleUserDao.getBySubroleid(subroleid);
		return JSONArray.fromObject(list).toString();
	}
	@Override
	public String getRoleUserTree(String roleid) {
		List list=tawSystemSubRoleUserDao.getRoleUserTree(roleid);
		return JSONArray.fromObject(list).toString();
	}
	@Override
	public String adds(List<TawSystemSubroleuser> subroleusers) {
		JSONObject jSONObject=new JSONObject();		
		String[] featurecodes =new String[subroleusers.size()];//特征码集合
		String key;
		TawSystemSubroleuser subroleuser;
		Iterator<TawSystemSubroleuser> itr=subroleusers.iterator();
		int i=0;
		while(itr.hasNext()){
			subroleuser=itr.next();
			key=subroleuser.getRoleid()+subroleuser.getSubroleid()+subroleuser.getUserid();//特征码规则
			key=Base64.getEncoder().encodeToString(key.getBytes());
			subroleuser.setFeaturecode(key);
			featurecodes[i]=key;
			i++;
		}
		List<TawSystemSubroleuser> list=tawSystemSubRoleUserDao.isExist(featurecodes);
		if(list.size()>0){
			jSONObject.accumulate("status", "2");
			JSONArray jSONArray=new JSONArray();
			for(TawSystemSubroleuser roleuser:list){
				jSONArray.add(roleuser.getUsername());
			}
			jSONObject.accumulate("msg", jSONArray);
		}else{
			for(TawSystemSubroleuser roleuser:subroleusers){			
				tawSystemSubRoleUserDao.save(roleuser);
			}
			jSONObject.accumulate("status", "1");
		}
		return jSONObject.toString();
	}	
	/*get、set*/
	@Override
	@Resource(name="tawSystemSubRoleUserDao")
	public void setBasedao(BaseDao<TawSystemSubroleuser> basedao) {
		super.setBasedao(basedao);		
	}


}
