package com.jecp.sysmanage.dao.impl;

import java.io.File;
import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.common.util.StrUtil;
import com.jecp.common.util.TimeUtil;
import com.jecp.sysmanage.dao.TawSystemAttachmentDao;
import com.jecp.sysmanage.model.TawSystemAttachment;
@Repository(value="tawSystemAttachmentDao")
@Scope("prototype")
public class TawSystemAttachmentDaoImpl extends BaseDaoImpl<TawSystemAttachment> implements TawSystemAttachmentDao{

	@Override
	public TawSystemAttachment upload(String realPath,MultipartFile multipartFile, TawSystemAttachment tawSystemAttachment,boolean dateSplit) throws Exception {
		String rpath=tawSystemAttachment.getPath().replace('\\', '/')+"/"+TimeUtil.getYearAndMonth();//相对路径
		if(!dateSplit){
			rpath=tawSystemAttachment.getPath().replace('\\', '/');//相对路径
		}
		String path=realPath+rpath;
		String filename=TimeUtil.getcuTimeBySSS()+tawSystemAttachment.getFilecnname().substring(tawSystemAttachment.getFilecnname().lastIndexOf('.'));
		File file = new File(path+"/"+filename);
		if(!file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}
		multipartFile.transferTo(file);
		String id=StrUtil.getUUID();
		tawSystemAttachment.setId(id);
		tawSystemAttachment.setFileenname(filename);
		tawSystemAttachment.setPath(rpath);
		tawSystemAttachment.setAttachsize(String.valueOf(multipartFile.getSize()) );
		tawSystemAttachment.setUploadtime(Timestamp.valueOf(TimeUtil.getcuTime()) ); 
		save(tawSystemAttachment);
		return getById(id);
	}
	@Transactional("txManager")
	@Override
	public Boolean deleteFile(String realPath,String fileId) {		
		TawSystemAttachment tawSystemAttachment=getById(fileId);		
		File f = new File(realPath+tawSystemAttachment.getPath()+"/"+tawSystemAttachment.getFileenname());
		if(f.exists()){			
			f.delete();
			delete(fileId);
			return true;
		}
		return false;
	}
	

}
