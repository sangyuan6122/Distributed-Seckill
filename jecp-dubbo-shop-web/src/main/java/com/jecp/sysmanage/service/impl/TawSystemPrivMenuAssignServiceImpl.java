package com.jecp.sysmanage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemPrivDao;
import com.jecp.sysmanage.dao.TawSystemPrivMenuAssignDao;
import com.jecp.sysmanage.model.TawSystemPriv;
import com.jecp.sysmanage.model.TawSystemPrivMenu;
import com.jecp.sysmanage.model.TawSystemPrivMenuassign;
import com.jecp.sysmanage.service.TawSystemPrivMenuAssignService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("tawSystemPrivMenuAssignService")
public class TawSystemPrivMenuAssignServiceImpl extends BaseServiceImpl<TawSystemPrivMenuassign> implements TawSystemPrivMenuAssignService{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivMenuAssignDao tawSystemPrivMenuAssignDao;	
	@Resource
	private TawSystemPrivDao tawSystemPrivDao;
	
	@Override
	public String getunassign(String owner) {
		JSONObject jSONObject=new JSONObject();		
		JSONArray jSONArray =new JSONArray();
		JSONObject jSONObject1;
		try{
			List<TawSystemPrivMenu> list = tawSystemPrivMenuAssignDao.getUnAssign(owner);				
			for(int i=0;i<list.size();i++){
				jSONObject1= new JSONObject();
				jSONObject1.accumulate("id", list.get(i).getId());		
				jSONObject1.accumulate("menuname", list.get(i).getMenuname());
				jSONArray.add(jSONObject1);					
			}
			jSONObject.accumulate("rows", jSONArray);
		}catch(Exception e){
			jSONObject.accumulate("status", 0);
		}			
		return jSONObject.toString();
	}
	@Override
	public String getassigned(String owner) {
		JSONObject jSONObject=new JSONObject();		
		JSONArray jSONArray =new JSONArray();
		JSONObject jSONObject1;
		List<TawSystemPrivMenu> list = tawSystemPrivMenuAssignDao.getAssigned(owner);			
		for(int i=0;list!=null&&i<list.size();i++){
			jSONObject1= new JSONObject();
			jSONObject1.accumulate("id", list.get(i).getId());		
			jSONObject1.accumulate("menuname", list.get(i).getMenuname());
			jSONArray.add(jSONObject1);					
		}
		jSONObject.accumulate("rows", jSONArray);
		return jSONObject.toString();
	}
	@Override
	public String add(TawSystemPrivMenuassign tspm) {
		JSONObject jSONObject=new JSONObject();		
		JSONArray jSONArray =new JSONArray();
		try{
			if(tawSystemPrivMenuAssignDao.exist(tspm.getMenuid(), tspm.getOwner())){					
				jSONObject.accumulate("status", "2");//数据重复
			}else{
				List<TawSystemPriv> list =tawSystemPrivDao.getByMenuId(tspm.getMenuid());
				TawSystemPrivMenuassign tawSystemPrivMenuassign;
				for(int i=0;list!=null&&i<list.size();i++){
					tawSystemPrivMenuassign = new TawSystemPrivMenuassign();
					tawSystemPrivMenuassign.setMenuid(tspm.getMenuid());
					tawSystemPrivMenuassign.setMenulevel(list.get(i).getMenulevel());
					tawSystemPrivMenuassign.setOwner(tspm.getOwner());
					tawSystemPrivMenuassign.setType(tspm.getType());
					tawSystemPrivMenuassign.setPrivid(list.get(i).getPrivid());
					tawSystemPrivMenuassign.setUrl(list.get(i).getUrl());
					tawSystemPrivMenuAssignDao.save(tawSystemPrivMenuassign);
				}
				jSONObject.accumulate("status", "1");
			}
		}catch(Exception e){
			log.error("",e);
			jSONObject.accumulate("status", "0");			
		}
		return jSONObject.toString();
	}
	@Override
	public String del(String menuid, String owner) {
		JSONObject jSONObject=new JSONObject();
		try{			
			tawSystemPrivMenuAssignDao.del(menuid, owner);
			jSONObject.accumulate("status", "1");
		}catch(Exception e){
			log.error("",e);
			jSONObject.accumulate("status", "0");			
		}
		return jSONObject.toString();
	}
	/*set、get*/
	@Override
	@Resource(name="tawSystemPrivMenuAssignDao")
	public void setBasedao(BaseDao<TawSystemPrivMenuassign> basedao) {		
		super.setBasedao(basedao);
	}	
	
}
