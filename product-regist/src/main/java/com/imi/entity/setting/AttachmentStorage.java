package com.imi.entity.setting;

import java.sql.Blob;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.imi.base.entity.AbstractEntity;

/**
 * 附件数据存储。
 */
@Entity
@Table(name = "sys_attachments_storages")
public class AttachmentStorage extends AbstractEntity implements Cloneable {
	private static final long serialVersionUID = 1L;
	private Long size;
	private Date createTime;
	private Blob content;
	private Set<Attachment> attachments;

	public Long getSize() {
		return size;
	}
	@Column
	public Blob getContent() {
		return content;
	}
	@OneToMany(mappedBy="storage")
	public Set<Attachment> getAttachments() {
		return attachments;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}

	public void setContent(Blob content) {
		this.content = content;
	}
	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}