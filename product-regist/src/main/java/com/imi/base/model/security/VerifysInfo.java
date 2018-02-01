package com.imi.base.model.security;

import com.imi.entity.products.Verifys;
import com.softvan.model.Paging;

public class VerifysInfo extends Paging {

	private static final long serialVersionUID = 1L;
	private String productNO;
	
	private Verifys verifys;
	
	//注册人对核查产品发起的问题自述内容
	private String problemReadme;
	
	private Long current_user_id;
	
	
	// 操作状态
	private String statusString;
	// 操作说明
	private String remark;
	// 操作ID
	private String id;
	// 附件ID 多个附件已逗号链接
	private String fileIdJoin;
	// 核查状态
	private String verifyStatus;
	
	
	public String getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getFileIdJoin() {
		return fileIdJoin;
	}
	public void setFileIdJoin(String fileIdJoin) {
		this.fileIdJoin = fileIdJoin;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCurrent_user_id() {
		return current_user_id;
	}
	public void setCurrent_user_id(Long current_user_id) {
		this.current_user_id = current_user_id;
	}
	public String getProblemReadme() {
		return problemReadme;
	}
	public void setProblemReadme(String problemReadme) {
		this.problemReadme = problemReadme;
	}
	public String getProductNO() {
		return productNO;
	}
	public void setProductNO(String productNO) {
		this.productNO = productNO;
	}
	public Verifys getVerifys() {
		return verifys;
	}
	public void setVerifys(Verifys verifys) {
		this.verifys = verifys;
	}
	
	
	

}
