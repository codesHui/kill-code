package com.imi.base.model.reviews;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.imi.entity.products.Product;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.softvan.model.Paging;

/**
 * 用户信息。
 */
@JsonSerialize
public class ReviewsInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Role role;// 角色 1初级、2中级、3高级
	private Product product;// 产品
	private int status;// 0待审核1审核通过2上报中介3上报高级（核查）
	private String hasReviews;

	private User flowMan1, flowMan2, flowMan3;// 复查 初级、中级、高级
	private int flowCheck1, flowCheck2, flowCheck3;// 复查结果1通过2不通过(上报)
	private String flowReason1, flowReason2, flowReason3;// 复查结果
	private Date flowDate1, flowDate2, flowDate3;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHasReviews() {
		return hasReviews;
	}

	public void setHasReviews(String hasReviews) {
		this.hasReviews = hasReviews;
	}

	public User getFlowMan1() {
		return flowMan1;
	}

	public void setFlowMan1(User flowMan1) {
		this.flowMan1 = flowMan1;
	}

	public User getFlowMan2() {
		return flowMan2;
	}

	public void setFlowMan2(User flowMan2) {
		this.flowMan2 = flowMan2;
	}

	public User getFlowMan3() {
		return flowMan3;
	}

	public void setFlowMan3(User flowMan3) {
		this.flowMan3 = flowMan3;
	}

	public int getFlowCheck1() {
		return flowCheck1;
	}

	public void setFlowCheck1(int flowCheck1) {
		this.flowCheck1 = flowCheck1;
	}

	public int getFlowCheck2() {
		return flowCheck2;
	}

	public void setFlowCheck2(int flowCheck2) {
		this.flowCheck2 = flowCheck2;
	}

	public int getFlowCheck3() {
		return flowCheck3;
	}

	public void setFlowCheck3(int flowCheck3) {
		this.flowCheck3 = flowCheck3;
	}

	public String getFlowReason1() {
		return flowReason1;
	}

	public void setFlowReason1(String flowReason1) {
		this.flowReason1 = flowReason1;
	}

	public String getFlowReason2() {
		return flowReason2;
	}

	public void setFlowReason2(String flowReason2) {
		this.flowReason2 = flowReason2;
	}

	public String getFlowReason3() {
		return flowReason3;
	}

	public void setFlowReason3(String flowReason3) {
		this.flowReason3 = flowReason3;
	}

	public Date getFlowDate1() {
		return flowDate1;
	}

	public void setFlowDate1(Date flowDate1) {
		this.flowDate1 = flowDate1;
	}

	public Date getFlowDate2() {
		return flowDate2;
	}

	public void setFlowDate2(Date flowDate2) {
		this.flowDate2 = flowDate2;
	}

	public Date getFlowDate3() {
		return flowDate3;
	}

	public void setFlowDate3(Date flowDate3) {
		this.flowDate3 = flowDate3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// ReviewsInfo用来传查询条件对应的字段
	/*
	 * private int orderNo, status, productType, protectionType; //
	 * 产品注册号、产品类别、产品名称、产品语言、中文名称、条款保护期、险类名称、险种名称 private String productNO,
	 * productName, productLanguage, chineseName,offreason,riskType,riskName;
	 * private MemberUser user;// 联系人姓名、联系人电话、联系人邮箱、法律责任人\产品注册人
	 * 
	 * private Set<Attachment> attachment; private String title, keywords,
	 * description;// 备注
	 */

}