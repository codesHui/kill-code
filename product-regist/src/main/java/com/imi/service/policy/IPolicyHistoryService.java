package com.imi.service.policy;

import java.util.List;

import com.imi.base.service.IBaseDataService;
import com.imi.entity.reportPolicy.ReportPolicyHistory;
import com.imi.model.policy.PolicyHistoryInfo;

public interface IPolicyHistoryService extends IBaseDataService<PolicyHistoryInfo>{

	public Long savePolicyReportHistory(ReportPolicyHistory policyHistory);

	public List<ReportPolicyHistory> queryList(Long userId);
}
