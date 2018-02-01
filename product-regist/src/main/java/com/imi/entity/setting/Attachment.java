package com.imi.entity.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.imi.base.BaseEntity;

/**
 * 附件数据。

 */

@Entity
@Table(name = "sys_attachments")
public class Attachment extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String name,code,extension,contentType,filePath;
	private Long size;
	private AttachmentStorage storage;
	private String type;//上传结果   1上传成功	  0 上传失败
	private String filetype;  // 文件类型   1/注册条款    0/其他 2/图文


	@Column(name="name",length=256)
	public String getName() {
		return name;
	}
	
	@Column(name="code",length=64)
	public String getCode() {
		return code;
	}
	@Column(name="type",length=1)
	public String getType() {
		return type;
	}
	
	@Column(name="extension",length=32)
	public String getExtension() {
		return extension;
	}
	@Column(name="contentType",length=64)
	public String getContentType() {
		return contentType;
	}
	public Long getSize() {
		return size;
	}
	@Column(name="filePath",length=500)
	public String getFilePath() {
		return filePath;
	}
	
	@ManyToOne
	@JoinColumn(name="storage_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public AttachmentStorage getStorage() {
		return storage;
	}
	


	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public void setStorage(AttachmentStorage storage) {
		this.storage = storage;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}


	
	
	


}