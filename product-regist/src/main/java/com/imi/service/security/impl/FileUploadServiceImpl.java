package com.imi.service.security.impl;

import java.io.File;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import com.imi.base.model.AttachmentInfo;
import com.imi.dao.setting.IAttachmentDao;
import com.imi.dao.setting.IAttachmentStorageDao;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.AttachmentStorage;
import com.imi.service.security.IFileUploadService;
import com.softvan.utils.IOUtil;
/**
 * 文件上传服务实现。
 */
public class FileUploadServiceImpl implements IFileUploadService {
	private static Logger logger = Logger.getLogger(FileUploadServiceImpl.class);
	private String tempStoragePath;
	private IAttachmentDao attachmentDao;
	private IAttachmentStorageDao attachmentStorageDao;
	/**
	 * 设置附件下载临时存储路径。
	 * @param tempStoragePath 
	 *	  附件下载临时存储路径。
	 */
	public void setTempStoragePath(String tempStoragePath) {
		if(logger.isDebugEnabled()) logger.debug(String.format("附件下载临时存储路径［%s］...", tempStoragePath));
		this.tempStoragePath = tempStoragePath;
	}
	/**
	 * 设置附件数据接口。
	 * @param attachmentDao 
	 *	  附件数据接口。
	 */
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		if(logger.isDebugEnabled()) logger.debug("注入附件数据接口...");
		this.attachmentDao = attachmentDao;
	}
	/**
	 * 设置附件存储数据接口。
	 * @param attachmentStorageDao 
	 *	  附件存储数据接口。
	 */
	public void setAttachmentStorageDao(IAttachmentStorageDao attachmentStorageDao) {
		if(logger.isDebugEnabled()) logger.debug("注入附件存储数据接口...");
		this.attachmentStorageDao = attachmentStorageDao;
	}
	/*
	 * 上传。
	 * @see com.softvan.service.IFileUploadService#upload(java.lang.String, java.lang.String, byte[])
	 */
	@Override
	public Long addUpload(String fileName, String contentType, byte[] data)throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("上传附件［%1$s  %2$s］...", fileName, contentType));
		String msg = null;
		if(StringUtils.isEmpty(fileName)){
			logger.error(msg = "附件文件名称为空！");
			throw new Exception(msg);
		}
		if(data == null || data.length == 0){
			logger.error(msg = "附件文件内容为空！");
			throw new Exception(msg);
		}
		Attachment attachment = new Attachment();
		attachment.setName(fileName);
		attachment.setExtension(IOUtil.getExtension(attachment.getName()));
		attachment.setSize((long)data.length);
		attachment.setCode(DigestUtils.md5DigestAsHex(data));
		attachment.setContentType(contentType);
		
		AttachmentStorage storage = this.attachmentStorageDao.updateAttachmentStorage(attachment, data);
		attachment.setStorage(storage);
		if(storage.getAttachments() == null){
			storage.setAttachments(new HashSet<Attachment>());
		}
		storage.getAttachments().add(attachment);
		return attachment.getId();
	}
	/*
	 * 下载。
	 * @see com.softvan.service.IFileUploadService#download(java.lang.String)
	 */
	@Override
	public AttachmentInfo download(String fileId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("下载附件［%s］...", fileId));
		String msg = null;
		if(StringUtils.isEmpty(fileId)){
			logger.error(msg = "附件ID为空！");
			throw new RuntimeException(msg);
		}
		Attachment attachment = this.attachmentDao.load(Attachment.class, fileId);
		if(attachment == null){
			logger.error(msg = String.format("附件［fileId ＝%s］不存在！", fileId));
		}
		String temp_file_name = String.format("%1$s%2$s", attachment.getCode(), attachment.getExtension());
		if(logger.isDebugEnabled()) logger.debug(String.format("临时存储附件文件名称：［%s］", temp_file_name));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String temp_dir = String.format("%1$s/%2$s", this.tempStoragePath,simpleDateFormat.format(attachment.getCreateTime()));
		if(logger.isDebugEnabled()) logger.debug(String.format("临时存储附件目录：［%s］", temp_dir));
		String temp_path = String.format("%1$s/%2$s", temp_dir,temp_file_name);
		if(logger.isDebugEnabled()) logger.debug(String.format("临时存储附件路径：［%s］", temp_path));
		File file = new File(temp_path);
		if(!file.exists()){
			File dir = new File(temp_dir);
			if(!dir.exists()) dir.mkdirs();
			Blob data = null;
			if(attachment.getStorage() == null || (data = attachment.getStorage().getContent()) == null){
				logger.error(msg = String.format("附件［%s］不存在！", attachment.getName()));
				throw new RuntimeException(msg);
			}
			//生成临时文件。
			byte[] buf = FileCopyUtils.copyToByteArray(data.getBinaryStream());
		   FileCopyUtils.copy(buf, file);
		}
		AttachmentInfo info = new AttachmentInfo(); 
		BeanUtils.copyProperties(attachment, info);
		info.setPath(temp_path);
		return info;
	}
}