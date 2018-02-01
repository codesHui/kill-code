package com.imi.dao.setting;

import com.imi.base.IBaseDao;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.AttachmentStorage;

/**
 * 附件存储数据接口。
 */
public interface IAttachmentStorageDao extends IBaseDao<AttachmentStorage> {
	/**
	 * 附件数据存储。
	 * @param attachment
	 * 附件信息。
	 * @param data
	 * 附件数据。
	 * @return
	 * @throws Exception
	 */
	AttachmentStorage updateAttachmentStorage(Attachment attachment,byte[] data) throws Exception;
}