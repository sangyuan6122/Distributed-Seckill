package com.jecp.sysmanage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemPrivDao;
import com.jecp.sysmanage.model.TawSystemAttachment;
import com.jecp.sysmanage.model.TawSystemPriv;
import com.jecp.sysmanage.service.TawSystemAttachmentService;
import com.jecp.sysmanage.service.TawSystemPrivService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service("tawSystemPrivService")
public class TawSystemPrivServiceImpl extends BaseServiceImpl<TawSystemPriv> implements TawSystemPrivService{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemPrivDao tawSystemPrivDao;
	@Resource
	private TawSystemAttachmentService tawSystemAttachmentService;
	@Override
	public String getMenu(String basePath,String userid, Long deptid,
			String pid, String showAll) {
		JSONArray jSONArray=new JSONArray();
		JSONObject jSONObject;		
		List<TawSystemPriv> plist =tawSystemPrivDao.getMenu(userid,deptid,pid,showAll);
		for(int i=0;i<plist.size();i++){
			String url="";
			jSONObject= new JSONObject();
			if(plist.get(i).getUrl()!=null||"1".equals(plist.get(i).getMenulevel())){
				if(plist.get(i).getUrl()!=null){
					String str = plist.get(i).getUrl().indexOf("?")>-1?"&":"?";
					url=plist.get(i).getUrl()+str+"l="+plist.get(i).getMenulevel();
					url=url.indexOf("http://")>-1?url:basePath+url;
				}else{
					url=basePath+"?l="+plist.get(i).getMenulevel();
				}
				jSONObject.accumulate("url", url);				
			}
			if("3".equals(plist.get(i).getMenulevel())){
				jSONObject.accumulate("iconCls", "other-button");				
			}
			jSONObject.accumulate("attributes", new JSONObject().accumulate("menulevel", plist.get(i).getMenulevel()));
			jSONObject.accumulate("id", plist.get(i).getPrivid());			
			jSONObject.accumulate("text", plist.get(i).getName());
			if(tawSystemPrivDao.getMenu(userid,deptid,plist.get(i).getPrivid().toString(),showAll).size()==0){
				jSONObject.accumulate("state", "open");
			}
			jSONObject.accumulate("state", "closed");
			jSONArray.add(jSONObject);					
		}
		return jSONArray.toString();
	}

	@Override
	public void delTree(String pid) {
		tawSystemPrivDao.delTree(pid);		
	}

	@Override
	public List<TawSystemPriv> getByMenuId(String menuid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTree(String pid) {
		TawSystemPriv tawSystemPriv=tawSystemPrivDao.getById(pid);
		JSONObject jSONObject=JSONObject.fromObject(tawSystemPriv);
		String ico=tawSystemPriv.getIco();		
		if(ico!=null){
			TawSystemAttachment tawSystemAttachment=tawSystemAttachmentService.getById(tawSystemPriv.getIco());
			jSONObject.accumulate("path", tawSystemAttachment.getPath()+"/"+tawSystemAttachment.getFileenname());
		}			
		return jSONObject.toString();
	}
	/*set、get*/
	@Override
	@Resource(name="tawSystemPrivDao")
	public void setBasedao(BaseDao<TawSystemPriv> basedao) {
		super.setBasedao(basedao);		
	}
	
}
