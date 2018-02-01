package com.imi.service.policy;

import java.util.List;
import java.util.Map;

import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.model.policy.PolicySummaryInfo;
import com.imi.model.policy.SummaryVo;

public interface IPolicySummaryService {
	
	public ReportSummary qryReportSymmary(PolicySummaryInfo sumInfo);

	public List<SummaryVo> queryList(Map model);
	
	public void queryListForPage(PolicySummaryInfo PolicySummaryInfo);
	
	public void saveSummaryInfo(List<ReportSummary> summaryLst);
}
