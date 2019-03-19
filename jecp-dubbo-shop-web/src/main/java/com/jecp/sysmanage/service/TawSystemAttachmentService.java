package com.jecp.sysmanage.service;

import org.springframework.web.multipart.MultipartFile;

import com.jecp.base.service.BaseService;
import com.jecp.sysmanage.model.TawSystemAttachment;

public interface TawSystemAttachmentService extends BaseService<TawSystemAttachment>{
	/**
	 * 附件上传
	 * @param readPath
	 * @param file
	 * @param tawSystemAttachment
	 * @return
	 */
	public String upload(String readPath,MultipartFile file,TawSystemAttachment tawSystemAttachment);
	/**
	 * 文件删除
	 * @param path
	 * @return
	 */
	public String deleteFile(String realPath,String fileId);
}
