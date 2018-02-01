package com.imi.entity.setting;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.entity.AbstractEntity;

/**
 * 过滤单词
 * 
 * @author josh
 *
 */
@Entity
@Table(name = "biz_sensitive_word")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "SensitiveWord")
public class SensitiveWord extends AbstractEntity {

	private static final long serialVersionUID = 8597746816279494991L;

	@Column(name = "content")
	private String content;
	@Column(name = "create_time")
	private Date createTime;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}