package com.imi.service.policy.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.policy.IPolicyHistoryDao;
import com.imi.entity.reportPolicy.ReportPolicyHistory;
import com.imi.model.policy.PolicyHistoryInfo;
import com.imi.service.policy.IPolicyHistoryService;

@Service
public class IPolicyHistoryServiceImpl extends BaseDataServiceImpl<ReportPolicyHistory, PolicyHistoryInfo> implements IPolicyHistoryService {

	@Resource
	private  IPolicyHistoryDao policyHistoryDao;
	
	@Override
	protected List<ReportPolicyHistory> find(PolicyHistoryInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PolicyHistoryInfo changeModel(ReportPolicyHistory data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long total(PolicyHistoryInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PolicyHistoryInfo update(PolicyHistoryInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long savePolicyReportHistory(ReportPolicyHistory policyHistory) {
		return policyHistoryDao.insertPolicyFileHistory(policyHistory);
	}

	@Override
	public List<ReportPolicyHistory> queryList(Long userId) {
		return policyHistoryDao.queryList(userId);
	}

}
