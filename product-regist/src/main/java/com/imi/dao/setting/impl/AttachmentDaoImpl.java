package com.imi.dao.setting.impl;

import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.setting.IAttachmentDao;
import com.imi.entity.setting.Attachment;

/**
 * 附件数据接口实现类。
 *
 */
@Repository("attachmentDao")
public class AttachmentDaoImpl extends BaseDaoImpl<Attachment> implements IAttachmentDao {

}