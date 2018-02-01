package com.imi.entity.products;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.BaseEntity;
import com.imi.entity.setting.Attachment;

@Entity
@Table(name = "biz_product_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_product_file")
public class ProductFile extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String productNO;// 产品编号

	private Integer type; // 1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉
	//private Integer opertype;//操作状态 与产品操作历史一直
	private Integer status;// 状态 1 是生效，状态2 是 未生效

	private Attachment attachment; // 附件数据

	@Column(name = "productNO")
	public String getProductNO() {
		return productNO;
	}

	public Integer getType() {
		return type;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "attachment",referencedColumnName="id")
	public Attachment getAttachment() {
		return attachment;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public void setProductNO(String productNO) {
		this.productNO = productNO;
	}

	
}
