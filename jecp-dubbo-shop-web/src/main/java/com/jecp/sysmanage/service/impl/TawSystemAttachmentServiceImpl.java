package com.jecp.sysmanage.service.impl;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jecp.base.dao.BaseDao;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.sysmanage.dao.TawSystemAttachmentDao;
import com.jecp.sysmanage.model.TawSystemAttachment;
import com.jecp.sysmanage.service.TawSystemAttachmentService;

import net.sf.json.JSONObject;
@Service("tawSystemAttachmentService")
public class TawSystemAttachmentServiceImpl  extends BaseServiceImpl<TawSystemAttachment> implements TawSystemAttachmentService{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemAttachmentDao tawSystemAttachmentDao;
	@Override
	public String upload(String readPath, MultipartFile file,TawSystemAttachment tawSystemAttachment){
		JSONObject jSONObject=new JSONObject();
		try {	
			if(tawSystemAttachment.getPath()!=null){
				TawSystemAttachment attach=tawSystemAttachmentDao.upload(readPath,file, tawSystemAttachment,false);			
				jSONObject.accumulate("id",attach.getId());
				jSONObject.accumulate("enname", attach.getFileenname());
				jSONObject.accumulate("cname", attach.getFilecnname());
				jSONObject.accumulate("url", attach.getPath()+"/"+attach.getFileenname());
			}else{
				jSONObject.accumulate("status", "0");
			}			
		} catch (Exception e) {			
			log.error("",e);
			jSONObject.accumulate("status", "0");
		}	
		return jSONObject.toString();
	}


	@Override
	public String deleteFile(String realPath, String fileId) {
		JSONObject jSONObject=new JSONObject();
		if(tawSystemAttachmentDao.deleteFile(realPath, fileId) ){
			jSONObject.accumulate("status", "1");
		}else{
			jSONObject.accumulate("status", "0");
		}
		return jSONObject.toString();
	}
	
	/*set、get*/
	@Override
	@Resource(name="tawSystemAttachmentDao")
	public void setBasedao(BaseDao<TawSystemAttachment> basedao) {		
		super.setBasedao(basedao);
	}	
}
