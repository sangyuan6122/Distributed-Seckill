package com.jecp.sysmanage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemPrivMenuDao;
import com.jecp.sysmanage.dao.TawSystemPrivMenuitemDao;
import com.jecp.sysmanage.model.TawSystemPrivMenu;
import com.jecp.sysmanage.service.TawSystemPrivMenuService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("tawSystemPrivMenuService")
public class TawSystemPrivMenuServiceImpl extends BaseServiceImpl<TawSystemPrivMenu> implements TawSystemPrivMenuService{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivMenuDao tawSystemPrivMenuDao;
	@Resource
	private TawSystemPrivMenuitemDao tawSystemPrivMenuitemDao;
	@Override
	public String load(Integer beginIndex, Integer size) {
		JSONObject jSONObject=new JSONObject();
		JSONArray jSONArray=new JSONArray();
		Long count =tawSystemPrivMenuDao.getCount("select count(*) from TawSystemPrivMenu");
		List<TawSystemPrivMenu> list =tawSystemPrivMenuDao.pageQueryHql("from TawSystemPrivMenu",beginIndex, size);
		jSONObject.accumulate("total", count);
		for(int i=0;i<list.size();i++){
			jSONObject= new JSONObject();
			jSONObject.accumulate("id", list.get(i).getId());		
			jSONObject.accumulate("menuname", list.get(i).getMenuname());
			if("M".equals(list.get(i).getClienttype())){
				jSONObject.accumulate("clienttype", "移动端");
			}else{
				jSONObject.accumulate("clienttype", "电脑端");
			}			
			jSONObject.accumulate("operateuserid", list.get(i).getOperateuserid());
			jSONObject.accumulate("remark", list.get(i).getRemark());
		    
			jSONArray.add(jSONObject);					
		}
		jSONObject.accumulate("rows", jSONArray);		
		return jSONObject.toString();
	}
	@Override
	public String add(TawSystemPrivMenu tawSystemPrivMenu) {
		JSONObject jSONObject=new JSONObject();
		try{
			String menuid = tawSystemPrivMenuDao.save(tawSystemPrivMenu).toString();
			tawSystemPrivMenuitemDao.addRootTree(menuid,tawSystemPrivMenu.getClienttype());
			jSONObject.accumulate("status", "1");
		}catch (Exception e) {	
			log.error("",e);
			jSONObject.accumulate("status", "0");
		}
		return jSONObject.toString();
	}
	@Override
	@Transactional("txManager")
	public String del(String id) {
		JSONObject jSONObject=new JSONObject();
		try{
			tawSystemPrivMenuDao.delete(id);
			tawSystemPrivMenuitemDao.delByMenuId(id);
			jSONObject.accumulate("status", "1");
		}catch (Exception e) {	
			log.error("",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
			jSONObject.accumulate("status", "0");
		}
		return jSONObject.toString();
	}
	@Override
	public String get(String id) {
		TawSystemPrivMenu tawSystemPrivMenu =tawSystemPrivMenuDao.getById(id);
		JSONObject jSONObject=new JSONObject();
		if(tawSystemPrivMenu!=null){
			jSONObject.accumulate("id", tawSystemPrivMenu.getId());
			jSONObject.accumulate("menuname", tawSystemPrivMenu.getMenuname());
			jSONObject.accumulate("clienttype", tawSystemPrivMenu.getClienttype());
			jSONObject.accumulate("operateuserid", tawSystemPrivMenu.getOperateuserid());
			jSONObject.accumulate("remark", tawSystemPrivMenu.getRemark());				
		}		
		return jSONObject.toString();
	}
	/*set、get*/
	@Override
	@Resource(name="tawSystemPrivMenuDao")
	public void setBasedao(BaseDao<TawSystemPrivMenu> basedao) {		
		super.setBasedao(basedao);
	}	
	
	

}
