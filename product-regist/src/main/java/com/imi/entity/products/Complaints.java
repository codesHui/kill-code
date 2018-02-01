package com.imi.entity.products;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.entity.AbstractEntity;
import com.imi.entity.security.User;

/**
 * 投诉管理
 * @author josh
 *
 */
@Entity
@Table(name = "biz_complaints")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_complaints")
public class Complaints extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Product product;// 产品
	private String name;
	private String phone;

	private String reason;//投诉理由
	private int status;//投诉状态  1/已处理  2/未处理  3/无需处理
	private Date createTime;
	
	
	private User resultMan;//处理人
	private int result;// 处理结果  1/无理投诉   2/合理投诉
	private String resultReason;// 处理理由
	private Date resultDate;// 结果时间
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "productNO",referencedColumnName="productNO")
	public Product getProduct() {
		return product;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getReason() {
		return reason;
	}
	public int getStatus() {
		return status;
	}
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "resultMan_id", nullable = true, updatable = false)
	public User getResultMan() {
		return resultMan;
	}
	public int getResult() {
		return result;
	}
	public String getResultReason() {
		return resultReason;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setResultMan(User resultMan) {
		this.resultMan = resultMan;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public void setResultReason(String resultReason) {
		this.resultReason = resultReason;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
}
