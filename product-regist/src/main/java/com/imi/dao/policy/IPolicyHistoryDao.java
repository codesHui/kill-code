package com.imi.dao.policy;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.reportPolicy.ReportPolicyHistory;

/**
 * 保单上报轨迹DAO接口
 * @author Devon
 *
 */
public interface IPolicyHistoryDao extends IBaseDao<ReportPolicyHistory>{

	/**
	 * 保存轨迹
	 * @param policyHistory
	 * @return
	 */
	public Long insertPolicyFileHistory(ReportPolicyHistory policyHistory);

	public List<ReportPolicyHistory> queryList(Long userId);

}
