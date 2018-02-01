package com.imi.model.policy;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.imi.entity.reportPolicy.ReportPolicyHistory;
import com.softvan.model.Paging;

@JsonSerialize(include = Inclusion.NON_NULL)
public class PolicyHistoryInfo extends Paging{
	
	private static final long serialVersionUID = 1L;
	private Long currentUserId;
	
	private List<PolicyHistoryInfo> listInfo;
	private List<ReportPolicyHistory> listHistory;
	private ReportPolicyHistory policyHistory;
	private int total;
	private int currentRowNo;
	public Long getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}
	public List<PolicyHistoryInfo> getListInfo() {
		return listInfo;
	}
	public void setListInfo(List<PolicyHistoryInfo> listInfo) {
		this.listInfo = listInfo;
	}
	public List<ReportPolicyHistory> getListHistory() {
		return listHistory;
	}
	public void setListHistory(List<ReportPolicyHistory> listHistory) {
		this.listHistory = listHistory;
	}
	public ReportPolicyHistory getPolicyHistory() {
		return policyHistory;
	}
	public void setPolicyHistory(ReportPolicyHistory policyHistory) {
		this.policyHistory = policyHistory;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentRowNo() {
		return currentRowNo;
	}
	public void setCurrentRowNo(int currentRowNo) {
		this.currentRowNo = currentRowNo;
	}	
}
