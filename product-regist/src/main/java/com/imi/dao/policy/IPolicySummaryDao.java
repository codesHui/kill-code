package com.imi.dao.policy;

import java.util.List;
import java.util.Map;

import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.model.policy.PolicySummaryInfo;
import com.imi.model.policy.SummaryVo;

public interface IPolicySummaryDao {

	public ReportSummary qryReportSymmary(PolicySummaryInfo sumInfo);

	public Long insertPolicySummary(ReportSummary summary);

	public void updatePolicySummary(ReportSummary summary);

	public List<SummaryVo> queryList(Map condition);
	
	public void queryListForPage(PolicySummaryInfo PolicySummaryInfo);
}
