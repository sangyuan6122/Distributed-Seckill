package com.jecp.sysmanage.dao;

import org.springframework.web.multipart.MultipartFile;

import com.jecp.base.dao.BaseDao;
import com.jecp.sysmanage.model.TawSystemAttachment;

public interface TawSystemAttachmentDao extends BaseDao<TawSystemAttachment>{
	/**
	 * 附件上传
	 * @param readPath
	 * @param file
	 * @param tawSystemAttachment
	 * @param dateSplit
	 * @return
	 * @throws Exception
	 */
	public TawSystemAttachment upload(String readPath,MultipartFile file,TawSystemAttachment tawSystemAttachment,boolean dateSplit)  throws Exception;
	/**
	 * 文件删除
	 * @param path
	 * @return
	 */
	public Boolean deleteFile(String realPath,String fileId);
	
	
}
