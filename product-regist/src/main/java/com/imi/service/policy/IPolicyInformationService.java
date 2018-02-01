package com.imi.service.policy;

import java.util.List;

import com.imi.base.service.IBaseDataService;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.setting.MemberUser;
import com.imi.model.policy.PolicyInformation;

public interface IPolicyInformationService extends IBaseDataService<PolicyInformation> {
	public void queryList(PolicyInformation policyInformation);
	
	public ReportPolicy queryDetails(Long policyId);
	
	public ReportPolicy qryPolicyByPolicyNo(String policyNo);
	
	public List<MemberUser> getAllCompany(Long currentId);
	
	public void savePolicyInfoBatch(List<ReportPolicy> policyLst,List<String> policyNoLst);
	
}
