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
import com.jecp.common.model.Tree;
import com.jecp.sysmanage.dao.TawSystemPrivDao;
import com.jecp.sysmanage.dao.TawSystemPrivMenuAssignDao;
import com.jecp.sysmanage.dao.TawSystemPrivMenuitemDao;
import com.jecp.sysmanage.model.TawSystemPriv;
import com.jecp.sysmanage.model.TawSystemPrivMenuassign;
import com.jecp.sysmanage.model.TawSystemPrivMenuitem;
import com.jecp.sysmanage.service.TawSystemPrivMenuitemService;
import com.jecp.sysmanage.vo.MenuAssignOwnerVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("tawSystemPrivMenuitemService")
public class TawSystemPrivMenuitemServiceImpl extends BaseServiceImpl<TawSystemPrivMenuitem> implements TawSystemPrivMenuitemService{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivMenuitemDao tawSystemPrivMenuitemDao;
	@Resource
	private TawSystemPrivMenuAssignDao tawSystemPrivMenuAssignDao;
	@Resource
	private TawSystemPrivDao tawSystemPrivDao;
	
	@Override
	public String getTree(String menuid,String clientType) {
		List<Tree> list = tawSystemPrivMenuitemDao.getTree(menuid,clientType);		
		String json = JSONArray.fromObject(list).toString();
		return json;
	}
	@Override
	@Transactional("txManager")
	public String add(TawSystemPrivMenuitem tawSystemPrivMenuitem) {
		JSONObject jSONObject=new JSONObject();
		try{
			if(tawSystemPrivMenuitemDao.isHaveParent(tawSystemPrivMenuitem.getMenuid(),tawSystemPrivMenuitem.getPrivid())){			
				tawSystemPrivMenuitemDao.save(tawSystemPrivMenuitem);
				List<MenuAssignOwnerVO> owner = tawSystemPrivMenuAssignDao.getByMenuid(tawSystemPrivMenuitem.getMenuid());
				TawSystemPrivMenuassign tawSystemPrivMenuassign;
				TawSystemPriv tawSystemPriv ;
				for(int i=0;owner!=null&&i<owner.size();i++){
					tawSystemPrivMenuassign = new TawSystemPrivMenuassign();
					tawSystemPriv= new TawSystemPriv();
					tawSystemPriv=tawSystemPrivDao.getById(tawSystemPrivMenuitem.getPrivid());
					tawSystemPrivMenuassign.setMenuid(tawSystemPrivMenuitem.getMenuid());
					tawSystemPrivMenuassign.setOwner(owner.get(i).getOwner());
					tawSystemPrivMenuassign.setType(owner.get(i).getType());
					tawSystemPrivMenuassign.setMenulevel(tawSystemPriv.getMenulevel());
					tawSystemPrivMenuassign.setPrivid(tawSystemPriv.getPrivid());
					tawSystemPrivMenuassign.setUrl(tawSystemPriv.getUrl());
					tawSystemPrivMenuAssignDao.save(tawSystemPrivMenuassign);
					tawSystemPrivMenuassign=null;
				}
				jSONObject.accumulate("status", "1");
			}else{
				jSONObject.accumulate("status", "请先添加父节点!");
			}	
		}catch (Exception e) {	
			jSONObject.accumulate("status", "0");
			log.error("",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}		
		return jSONObject.toString();
	}
	@Override
	@Transactional("txManager")
	public String delAllTree(String menuid, String privid) {
		JSONObject jSONObject=new JSONObject();
		try{
			tawSystemPrivMenuitemDao.delAllTree(menuid,privid);
			tawSystemPrivMenuAssignDao.delByMenuid(menuid, privid);
			jSONObject.accumulate("status", "1");
		}catch (Exception e) {	
			jSONObject.accumulate("status", "0");
			log.error("",e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
		}
		return jSONObject.toString();
	}
	/*set、get*/
	@Override
	@Resource(name="tawSystemPrivMenuitemDao")
	public void setBasedao(BaseDao<TawSystemPrivMenuitem> basedao) {		
		super.setBasedao(basedao);
	}	
	
}
