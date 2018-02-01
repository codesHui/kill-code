package com.imi.model.report;

import java.io.Serializable;

public class ReportVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String companyName;

	private String productType;

	private String riskType;

	private String riskName;

	private int registNum;

	private int cancelNum;
	private int verifyNum;

	private int problemNum;

	private int chineseNum;

	private int englishNum;
	private int otherNum;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public int getRegistNum() {
		return registNum;
	}
	public void setRegistNum(int registNum) {
		this.registNum = registNum;
	}
	public int getCancelNum() {
		return cancelNum;
	}
	public void setCancelNum(int cancelNum) {
		this.cancelNum = cancelNum;
	}
	public int getProblemNum() {
		return problemNum;
	}
	public void setProblemNum(int problemNum) {
		this.problemNum = problemNum;
	}
	public int getChineseNum() {
		return chineseNum;
	}
	public void setChineseNum(int chineseNum) {
		this.chineseNum = chineseNum;
	}
	public int getEnglishNum() {
		return englishNum;
	}
	public void setEnglishNum(int englishNum) {
		this.englishNum = englishNum;
	}
	public int getOtherNum() {
		return otherNum;
	}
	public void setOtherNum(int otherNum) {
		this.otherNum = otherNum;
	}
	public int getVerifyNum() {
		return verifyNum;
	}
	public void setVerifyNum(int verifyNum) {
		this.verifyNum = verifyNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	

}
