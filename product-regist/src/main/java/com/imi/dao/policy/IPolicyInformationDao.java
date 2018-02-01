package com.imi.dao.policy;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.model.policy.PolicyInformation;

/**
 * 查询保单信息接口
 * @author Administrator
 *
 */
public interface IPolicyInformationDao extends IBaseDao<ReportPolicy>{
	
	public void queryList(PolicyInformation info);
	
	public ReportPolicy queryDetails(Long policyId);
	
	/**
	 * 根据保单号查询保单信息
	 * @return
	 */
	public ReportPolicy qryInfoByPolicyNo(String policyNo);
	
	/**
	 * 新增保单信息
	 * @param data
	 * @return
	 */
	public void insertPolicyBatch(List<ReportPolicy> policyLst);
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public void updatePolicy(ReportPolicy data);
		
}
