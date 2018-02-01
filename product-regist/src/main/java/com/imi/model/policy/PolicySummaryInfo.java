package com.imi.model.policy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.entity.setting.MemberUser;
import com.softvan.model.Paging;

/**
 * 保单汇总信息
 * @author Devon
 *
 */
public class PolicySummaryInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private Long currentUserId;
	private MemberUser user;// 联系人姓名、联系人电话、联系人邮箱、法律责任人\产品注册人
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
	//分页
	private String total;
	private int currentRowNo;
	private String summaryStartTime;
	private String summaryEndTime;
	private List<ReportSummary> list;
	private ReportSummary summary;
	private List<PolicySummaryInfo> listInfo;
	
	public String getSummaryStartTime() {
		return summaryStartTime;
	}
	public void setSummaryStartTime(String summaryStartTime) {
		this.summaryStartTime = summaryStartTime;
	}
	public String getSummaryEndTime() {
		return summaryEndTime;
	}
	public void setSummaryEndTime(String summaryEndTime) {
		this.summaryEndTime = summaryEndTime;
	}
	public Long getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}
	public MemberUser getUser() {
		return user;
	}
	public void setUser(MemberUser user) {
		this.user = user;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getReportUnit() {
		return reportUnit;
	}
	public void setReportUnit(String reportUnit) {
		this.reportUnit = reportUnit;
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
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public int getCurrentRowNo() {
		return currentRowNo;
	}
	public void setCurrentRowNo(int currentRowNo) {
		this.currentRowNo = currentRowNo;
	}
	public List<ReportSummary> getList() {
		return list;
	}
	public void setList(List<ReportSummary> list) {
		this.list = list;
	}
	public ReportSummary getSummary() {
		return summary;
	}
	public void setSummary(ReportSummary summary) {
		this.summary = summary;
	}
	public List<PolicySummaryInfo> getListInfo() {
		return listInfo;
	}
	public void setListInfo(List<PolicySummaryInfo> listInfo) {
		this.listInfo = listInfo;
	}
	@Override
	public String toString() {
		return "PolicySummaryInfo [currentUserId=" + currentUserId + ", user=" + user + ", riskName=" + riskName
				+ ", riskType=" + riskType + ", reportUnit=" + reportUnit + ", reportYear=" + reportYear
				+ ", reportMonth=" + reportMonth + ", premUSD=" + premUSD + ", premJPY=" + premJPY + ", premEUR="
				+ premEUR + ", premGBP=" + premGBP + ", premCHF=" + premCHF + ", prem=" + prem + ", createdTime="
				+ createdTime + ", modifiedTime=" + modifiedTime + ", total=" + total + ", currentRowNo=" + currentRowNo
				+ ", summaryStartTime=" + summaryStartTime + ", summaryEndTime=" + summaryEndTime + ", list=" + list
				+ ", summary=" + summary + ", listInfo=" + listInfo + "]";
	}
	
}
