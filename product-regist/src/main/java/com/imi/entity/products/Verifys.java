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
import com.imi.entity.security.User;

/**
 * 核查申请表
 * 
 * @author josh
 *
 */
@Entity
@Table(name = "biz_verifys")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_verifys")
public class Verifys extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Product product;// 产品
	private int status;//核查状态   
	private User verifyMan;// 核查发起人
	private String verifyReason;// 核查理由
	private Date  verifyDate; //发起核查时间
	private User resultMan;//处理人
	private int result;// 处理结果
	private String resultReason;// 处理原因
	private Date resultDate;// 处理结果时间
	private Boolean isRecon;// 是否复议
	private String reconReason;// 复议原因
	private User reconMan;// 复议人
	private Date reconDate;// 复议时间
	private int reconResult;// 复议结果
	private String reconResultReason;// 复议结果原因
	
	//记录复议次数 
	private int reviewNum;
	// 核查数据来源   1/复查  2/投诉
	private int source;
	
	// 是否已发送注销产品站内信  1/是  0/否
	private int logoutType;

	public int getLogoutType() {
		return logoutType;
	}

	public void setLogoutType(int logoutType) {
		this.logoutType = logoutType;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getReviewNum() {
		return reviewNum;
	}

	public void setReviewNum(int reviewNum) {
		this.reviewNum = reviewNum;
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
	@JoinColumn(name = "verifyMan_id", nullable = true, updatable = false)
	public User getVerifyMan() {
		return verifyMan;
	}

	public String getVerifyReason() {
		return verifyReason;
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

	public Boolean getIsRecon() {
		return isRecon;
	}

	public String getReconReason() {
		return reconReason;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "reconMan_id", nullable = true, updatable = false)
	public User getReconMan() {
		return reconMan;
	}

	public Date getReconDate() {
		return reconDate;
	}

	public int getReconResult() {
		return reconResult;
	}

	public String getReconResultReason() {
		return reconResultReason;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setVerifyMan(User verifyMan) {
		this.verifyMan = verifyMan;
	}

	public void setVerifyReason(String verifyReason) {
		this.verifyReason = verifyReason;
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

	public void setIsRecon(Boolean isRecon) {
		this.isRecon = isRecon;
	}

	public void setReconReason(String reconReason) {
		this.reconReason = reconReason;
	}

	public void setReconMan(User reconMan) {
		this.reconMan = reconMan;
	}

	public void setReconDate(Date reconDate) {
		this.reconDate = reconDate;
	}

	public void setReconResult(int reconResult) {
		this.reconResult = reconResult;
	}

	public void setReconResultReason(String reconResultReason) {
		this.reconResultReason = reconResultReason;
	}


	public void setResultMan(User resultMan) {
		this.resultMan = resultMan;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	

}
