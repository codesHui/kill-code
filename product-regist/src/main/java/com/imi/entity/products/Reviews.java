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

import com.imi.base.BaseEntity;
import com.imi.base.entity.AbstractEntity;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
/**
 * 复核申请表
 * @author josh
 *
 */
@Entity
@Table(name = "biz_reviews")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_reviews")
public class Reviews extends BaseEntity {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private Role role;//角色 1初级、2中级、3高级
	private Product product;//产品
	
	private int  status;//0待审核1审核通过2上报中介3上报高级（核查）
	
	private User flowMan1,flowMan2,flowMan3;//复查 初级、中级、高级
	
	private int flowCheck1,flowCheck2,flowCheck3;//复查结果1通过2不通过(上报)
	private String flowReason1,flowReason2,flowReason3;//复查结果
	private String flowOperate1,flowOperate2,flowOperate3;//复查操作
	private Date flowDate1,flowDate2,flowDate3;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "role_code",referencedColumnName="code",nullable = false, updatable = true)
	public Role getRole() {
		return role;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "productNO",referencedColumnName="productNO")
	public Product getProduct() {
		return product;
	}

	public int getStatus() {
		return status;
	}
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "flowMan1_id", nullable = true, updatable = true)
	public User getFlowMan1() {
		return flowMan1;
	}
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "flowMan2_id", nullable = true, updatable = true)
	public User getFlowMan2() {
		return flowMan2;
	}
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "flowMan3_id", nullable = true, updatable = true)
	public User getFlowMan3() {
		return flowMan3;
	}

	public int getFlowCheck1() {
		return flowCheck1;
	}

	public int getFlowCheck2() {
		return flowCheck2;
	}

	public int getFlowCheck3() {
		return flowCheck3;
	}

	public Date getFlowDate1() {
		return flowDate1;
	}

	public Date getFlowDate2() {
		return flowDate2;
	}

	public Date getFlowDate3() {
		return flowDate3;
	}
	public String getFlowReason1() {
		return flowReason1;
	}

	public String getFlowReason2() {
		return flowReason2;
	}
	public String getFlowReason3() {
		return flowReason3;
	}
	
	public String getFlowOperate1() {
		return flowOperate1;
	}

	public void setFlowOperate1(String flowOperate1) {
		this.flowOperate1 = flowOperate1;
	}

	public String getFlowOperate2() {
		return flowOperate2;
	}

	public void setFlowOperate2(String flowOperate2) {
		this.flowOperate2 = flowOperate2;
	}

	public String getFlowOperate3() {
		return flowOperate3;
	}

	public void setFlowOperate3(String flowOperate3) {
		this.flowOperate3 = flowOperate3;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setFlowMan1(User flowMan1) {
		this.flowMan1 = flowMan1;
	}

	public void setFlowMan2(User flowMan2) {
		this.flowMan2 = flowMan2;
	}

	public void setFlowMan3(User flowMan3) {
		this.flowMan3 = flowMan3;
	}

	public void setFlowCheck1(int flowCheck1) {
		this.flowCheck1 = flowCheck1;
	}

	public void setFlowCheck2(int flowCheck2) {
		this.flowCheck2 = flowCheck2;
	}

	public void setFlowCheck3(int flowCheck3) {
		this.flowCheck3 = flowCheck3;
	}

	public void setFlowDate1(Date flowDate1) {
		this.flowDate1 = flowDate1;
	}

	public void setFlowDate2(Date flowDate2) {
		this.flowDate2 = flowDate2;
	}

	public void setFlowDate3(Date flowDate3) {
		this.flowDate3 = flowDate3;
	}



	public void setFlowReason1(String flowReason1) {
		this.flowReason1 = flowReason1;
	}


	public void setFlowReason2(String flowReason2) {
		this.flowReason2 = flowReason2;
	}

	

	public void setFlowReason3(String flowReason3) {
		this.flowReason3 = flowReason3;
	}
 
}
