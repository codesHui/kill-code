package com.imi.model.policy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.imi.entity.reportPolicy.*;
import com.imi.entity.setting.MemberUser;
import com.softvan.model.Paging;
/**
 * 查询保单信息
 * @author Administrator
 *
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PolicyInformation extends Paging{
	private static final long serialVersionUID = 1L;
	private Long currentUserId;
	private MemberUser user;// 联系人姓名、联系人电话、联系人邮箱、法律责任人\产品注册人
	/**
	 * 保单中的数据字段
	 */
	private String policyNo;//上报的保单号
	private String appntName;//保险人名称
	private String insuredName;//被保险名称
	private String riskName;//险种名称
	private String riskType;//险种类型
	private String policyUploadUnit;//保单上报的会员单位
	private String franchise;//责任免赔额
	private BigDecimal prem;//保费
	private BigDecimal amount;//保额
	private String mainTerm;//主条款
	private String addTerm;//附加条款
	private String currency;//币种
	private String policyStartTime;//保险起始时间
	private String policyEndTime;//保险结束时间
	//private String policyStartTimeConvert;//保险起始时间
	//private String policyEndTimeConvert;//保险结束时间
	private BigDecimal policyRate;//总保险费率（%）
	private Date createdTime;//数据创建时间
	private Date modifiedTime;//数据修改时间
	
	
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getAppntName() {
		return appntName;
	}
	public void setAppntName(String appntName) {
		this.appntName = appntName;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
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
	public String getPolicyUploadUnit() {
		return policyUploadUnit;
	}
	public void setPolicyUploadUnit(String policyUploadUnit) {
		this.policyUploadUnit = policyUploadUnit;
	}
	public String getFranchise() {
		return franchise;
	}
	public void setFranchise(String franchise) {
		this.franchise = franchise;
	}
	public BigDecimal getPrem() {
		return prem;
	}
	public void setPrem(BigDecimal prem) {
		this.prem = prem;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getMainTerm() {
		return mainTerm;
	}
	public void setMainTerm(String mainTerm) {
		this.mainTerm = mainTerm;
	}
	public String getAddTerm() {
		return addTerm;
	}
	public void setAddTerm(String addTerm) {
		this.addTerm = addTerm;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPolicyStartTime() {
		return policyStartTime;
	}
	public void setPolicyStartTime(String policyStartTime) {
		this.policyStartTime = policyStartTime;
	}
	public String getPolicyEndTime() {
		return policyEndTime;
	}
	public void setPolicyEndTime(String policyEndTime) {
		this.policyEndTime = policyEndTime;
	}
	public BigDecimal getPolicyRate() {
		return policyRate;
	}
	public void setPolicyRate(BigDecimal policyRate) {
		this.policyRate = policyRate;
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
	private List<PolicyInformation> listInfo;
	private List<ReportPolicy> listPolicy;
	private List<MemberUser> listMemberUser;
	private ReportPolicy reportPolicy;
	private String total;
	private int currentRowNo;
	
	public List<MemberUser> getListMemberUser() {
		return listMemberUser;
	}
	public void setListMemberUser(List<MemberUser> listMemberUser) {
		this.listMemberUser = listMemberUser;
	}
	public Long getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}
	public ReportPolicy getReportPolicy() {
		return reportPolicy;
	}
	public void setReportPolicy(ReportPolicy reportPolicy) {
		this.reportPolicy = reportPolicy;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<PolicyInformation> getListInfo() {
		return listInfo;
	}
	public void setListInfo(List<PolicyInformation> listInfo) {
		this.listInfo = listInfo;
	}
	public List<ReportPolicy> getListPolicy() {
		return listPolicy;
	}
	public void setListPolicy(List<ReportPolicy> listPolicy) {
		this.listPolicy = listPolicy;
	}
	public MemberUser getUser() {
		return user;
	}
	public void setUser(MemberUser user) {
		this.user = user;
	}
	public int getCurrentRowNo() {
		return currentRowNo;
	}
	public void setCurrentRowNo(int currentRowNo) {
		this.currentRowNo = currentRowNo;
	}

}
