package com.jecp.sysmanage.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemDicttypeDao;
import com.jecp.sysmanage.model.TawSystemDicttype;
import com.jecp.sysmanage.service.TawSystemDicttypeService;

@Service("tawSystemDicttypeService")
public class TawSystemDicttypeServiceImpl extends BaseServiceImpl<TawSystemDicttype> implements TawSystemDicttypeService{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemDicttypeDao tawSystemDicttypeDao;

	@Override
	public String getDictTree(String pid) {
		TawSystemDicttype tawSystemDicttype=tawSystemDicttypeDao.getById(pid);
		return JSONObject.toJSONString(tawSystemDicttype);
	}

	@Override
	public int delDictTree(Integer dictid) {
		try {
			tawSystemDicttypeDao.delDictTree(dictid);
			return 1;
		}catch(Exception e) {
			log.error("",e);
			return 0;
		}		
	}

	@Override
	public int isRepeat(String parentdictid, String dictname, String dictid) {		
		return tawSystemDicttypeDao.isRepeat(parentdictid, dictname, dictid);
	}
	@Override
	public String get(Integer dictid) {
		TawSystemDicttype tawSystemDicttype=tawSystemDicttypeDao.getById(dictid);
		return JSONObject.toJSONString(tawSystemDicttype);
	}
	@Override
	public String getTree(String parentDictid) {
		JSONArray jSONArray=new JSONArray();
		JSONObject jSONObject;		
		List<TawSystemDicttype> plist = tawSystemDicttypeDao.getDictTree(parentDictid);		
		for(int i=0;i<plist.size();i++){
			jSONObject= new JSONObject();
			jSONObject.put("id", plist.get(i).getDictid());			
			jSONObject.put("text", plist.get(i).getDictname());
			if(tawSystemDicttypeDao.getDictTree(plist.get(i).getDictid().toString()).size()==0){
				jSONObject.put("state", "open");
			}
			jSONObject.put("state", "closed");
			jSONArray.add(jSONObject);					
		}		
		return jSONArray.toJSONString();
	}
	@Override
	public String getToJson(String parentDictid) {
		JSONArray jSONArray=new JSONArray();
		List<TawSystemDicttype> plist = new ArrayList<TawSystemDicttype>();
		JSONObject jSONObject;	
		plist=tawSystemDicttypeDao.getDictTree(parentDictid);
		for(int i=0;i<plist.size();i++){
			jSONObject= new JSONObject();
			jSONObject.put("id", plist.get(i).getDictid());			
			jSONObject.put("text", plist.get(i).getDictname());
			jSONArray.add(jSONObject);					
		}
		return jSONArray.toJSONString();
	}

	@Override
	public String getCodeToJson(String parentDictid) {
		JSONArray jSONArray=new JSONArray();
		List<TawSystemDicttype> plist = new ArrayList<TawSystemDicttype>();
		JSONObject jSONObject;	
		plist=tawSystemDicttypeDao.getDictTree(parentDictid);		
		for(int i=0;i<plist.size();i++){
			jSONObject= new JSONObject();
			jSONObject.put("id", plist.get(i).getDictcode());			
			jSONObject.put("text", plist.get(i).getDictname());
			jSONArray.add(jSONObject);					
		}
		return jSONArray.toJSONString();
	}
	/*set、get*/
	@Override
	@Resource(name="tawSystemDicttypeDao")
	public void setBasedao(BaseDao<TawSystemDicttype> basedao) {		
		super.setBasedao(basedao);
	}

	

	

	
}
