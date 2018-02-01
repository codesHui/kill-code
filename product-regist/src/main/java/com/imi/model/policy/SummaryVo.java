package com.imi.model.policy;

import java.io.Serializable;
import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class SummaryVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String reportUnit;
	private String riskType;
	private String riskName;
	private String reportYear;
	private String reportMonth;//汇总信息中的月份
	private BigDecimal premUSD;//保费-美元
	private BigDecimal premJPY;//保费-日元
	private BigDecimal premEUR;//保费-欧元
	private BigDecimal premGBP;//保费-英镑
	private BigDecimal premCHF;//保费-法郎
	private BigDecimal prem;//折币对应的保费
	
	public String getReportUnit() {
		return reportUnit;
	}
	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
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
	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	public String getReportMonth() {
		return reportMonth;
	}
	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}
	public BigDecimal getPremUSD() {
		return premUSD;
	}
	public void setPremUSD(BigDecimal premUSD) {
		this.premUSD = premUSD;
	}
	public BigDecimal getPremJPY() {
		return premJPY;
	}
	public void setPremJPY(BigDecimal premJPY) {
		this.premJPY = premJPY;
	}
	public BigDecimal getPremEUR() {
		return premEUR;
	}
	public void setPremEUR(BigDecimal premEUR) {
		this.premEUR = premEUR;
	}
	public BigDecimal getPremGBP() {
		return premGBP;
	}
	public void setPremGBP(BigDecimal premGBP) {
		this.premGBP = premGBP;
	}
	public BigDecimal getPremCHF() {
		return premCHF;
	}
	public void setPremCHF(BigDecimal premCHF) {
		this.premCHF = premCHF;
	}
	public BigDecimal getPrem() {
		return prem;
	}
	public void setPrem(BigDecimal prem) {
		this.prem = prem;
	}
}
