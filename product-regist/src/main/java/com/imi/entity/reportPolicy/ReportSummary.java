package com.imi.entity.reportPolicy;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.entity.AbstractEntity;

/**
 * 登记的汇总信息表
 * @author Devon
 *
 */
@Entity
@Table(name = "biz_policy_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_policy_summary")
public class ReportSummary extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	private String riskName;//险种名称
	private String riskType;//险种类型
	private String reportUnit;//汇总信息上报的会员单位
	private String reportYear;//汇总信息中的年份
	private String reportMonth;//汇总信息中的月份
	private BigDecimal premUSD;//保费-美元
	private BigDecimal premJPY;//保费-日元
	private BigDecimal premEUR;//保费-欧元
	private BigDecimal premGBP;//保费-英镑
	private BigDecimal premCHF;//保费-法郎
	private BigDecimal prem;//折币对应的保费
	private Date createdTime;//数据创建时间
	private Date modifiedTime;//数据修改时间
	
	@Column(name="risk_name")
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	
	@Column(name="risk_type")
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	@Column(name="report_unit")
	public String getReportUnit() {
		return reportUnit;
	}
	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
	}
	
	@Column(name="report_year")
	public String getReportYear() {
		return reportYear;
	}
	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}
	
	@Column(name="report_month")
	public String getReportMonth() {
		return reportMonth;
	}
	public void setReportMonth(String reportMonth) {
		this.reportMonth = reportMonth;
	}
	
	@Column(name="prem_usd")
	public BigDecimal getPremUSD() {
		return premUSD;
	}
	public void setPremUSD(BigDecimal premUSD) {
		this.premUSD = premUSD;
	}
	
	@Column(name="prem_jpy")
	public BigDecimal getPremJPY() {
		return premJPY;
	}
	public void setPremJPY(BigDecimal premJPY) {
		this.premJPY = premJPY;
	}
	
	@Column(name="prem_eur")
	public BigDecimal getPremEUR() {
		return premEUR;
	}
	public void setPremEUR(BigDecimal premEUR) {
		this.premEUR = premEUR;
	}
	
	@Column(name="prem_gbp")
	public BigDecimal getPremGBP() {
		return premGBP;
	}
	public void setPremGBP(BigDecimal premGBP) {
		this.premGBP = premGBP;
	}
	
	@Column(name="prem_chf")
	public BigDecimal getPremCHF() {
		return premCHF;
	}
	public void setPremCHF(BigDecimal premCHF) {
		this.premCHF = premCHF;
	}
	
	@Column(name="prem")
	public BigDecimal getPrem() {
		return prem;
	}
	public void setPrem(BigDecimal prem) {
		this.prem = prem;
	}
	
	@Column(name="created_time")
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@Column(name="modified_time")
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public String toString() {
		return "ReportSummary [riskName=" + riskName + ", riskType=" + riskType + ", reportUnit=" + reportUnit
				+ ", reportYear=" + reportYear + ", reportMonth=" + reportMonth + ", premUSD=" + premUSD + ", premJPY="
				+ premJPY + ", premEUR=" + premEUR + ", premGBP=" + premGBP + ", premCHF=" + premCHF + ", prem=" + prem
				+ ", createdTime=" + createdTime + ", modifiedTime=" + modifiedTime + "]";
	}
	
}
