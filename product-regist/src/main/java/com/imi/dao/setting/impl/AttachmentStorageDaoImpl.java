package com.imi.dao.setting.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.setting.IAttachmentStorageDao;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.AttachmentStorage;

/**
 * 附件存储数据接口实现类。
 * 
 */
@Repository("attachmentStorageDao")
public class AttachmentStorageDaoImpl extends BaseDaoImpl<AttachmentStorage> implements IAttachmentStorageDao {
	private static final Logger logger = Logger.getLogger(AttachmentStorageDaoImpl.class);
	/*
	 * 附件数据存储。
	 * @see com.softvan.netplatform.dao.IAttachmentStorageDao#updateAttachmentStorage(com.softvan.netplatform.domain.Attachment, byte[])
	 */
	@Override
	public AttachmentStorage updateAttachmentStorage(Attachment attachment,byte[] data) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("更新附件存储...");
		if(attachment == null) throw new Exception("附件对象 attachment 为空！");
		AttachmentStorage storage = this.load(AttachmentStorage.class, attachment.getId());
		if(storage == null){
			if(data == null || data.length == 0) throw new Exception("附件数据为空！");
			storage =  new AttachmentStorage();	
			storage.setSize(attachment.getSize());
			storage.setContent(this.getLobHelper().createBlob(data));
			this.save(storage);
		}
		return storage;
	}

}