package com.imi.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.imi.base.entity.AbstractEntity;
import com.imi.entity.security.User;

/**
 * 基础的实体类
 *
 */
@MappedSuperclass
public abstract class BaseEntity extends AbstractEntity {

 
    /**
	 * 
	 */



	private User creator;


    private Date createTime;


    private User modifier;

 
    private Date modifiedTime;

    protected BaseEntity() {
    }

    protected BaseEntity(Long id) {
        super(id);
    }

    /** 创建者 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creater_id", nullable = true, updatable = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
    /** 创建时间 */
    @Column(name = "create_time", nullable = true, updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    /** 修改者 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifier_id", nullable = true)
    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }
    /** 修改时间 */
    @Column(name = "modified_time", nullable = true,  updatable = true)
    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }


}
