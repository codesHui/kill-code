package com.imi.service.policy.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.policy.IPolicySummaryDao;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.model.policy.PolicySummaryInfo;
import com.imi.model.policy.SummaryVo;
import com.imi.service.policy.IPolicySummaryService;

@Service
public class IPolicySummaryServiceImpl extends BaseDataServiceImpl<ReportSummary, PolicySummaryInfo> implements IPolicySummaryService {

	@Resource
	private  IPolicySummaryDao policySummaryDao;
	
	public void setPolicySummaryDao(IPolicySummaryDao policySummaryDao) {
		this.policySummaryDao = policySummaryDao;
	}

	@Override
	protected List<ReportSummary> find(PolicySummaryInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PolicySummaryInfo changeModel(ReportSummary data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Long total(PolicySummaryInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PolicySummaryInfo update(PolicySummaryInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ReportSummary qryReportSymmary(PolicySummaryInfo sumInfo) {
		ReportSummary summary = this.policySummaryDao.qryReportSymmary(sumInfo);
		 if( null == summary ){
			 return null;
		 }
		return summary;
	}

	@Override
	public List<SummaryVo> queryList(Map condition) {
		return this.policySummaryDao.queryList(condition);
	}

	@Override
	public void queryListForPage(PolicySummaryInfo PolicySummaryInfo) {
		// TODO Auto-generated method stub
		this.policySummaryDao.queryListForPage(PolicySummaryInfo);
	}

	@Override
	public void saveSummaryInfo(List<ReportSummary> summaryLst) {
		for(ReportSummary summary : summaryLst){
			
			PolicySummaryInfo sumInfo = new PolicySummaryInfo();
			sumInfo.setRiskType(summary.getRiskType());
			sumInfo.setReportUnit(summary.getReportUnit()); 
			sumInfo.setReportYear(summary.getReportYear());
			sumInfo.setReportMonth(summary.getReportMonth());
			
			ReportSummary newSummary = this.policySummaryDao.qryReportSymmary(sumInfo);
			if( null == newSummary ){
				summary.setCreatedTime(new Date());
				summary.setModifiedTime(new Date());
				this.policySummaryDao.insertPolicySummary(summary);
			}else{
				newSummary.setPrem(summary.getPrem());
				newSummary.setPremCHF(summary.getPremCHF());
				newSummary.setPremEUR(summary.getPremEUR());
				newSummary.setPremGBP(summary.getPremGBP());
				newSummary.setPremJPY(summary.getPremJPY());
				newSummary.setPremUSD(summary.getPremUSD());
				newSummary.setReportMonth(summary.getReportMonth());
				newSummary.setReportUnit(summary.getReportUnit());
				newSummary.setReportYear(summary.getReportYear());
				newSummary.setRiskName(summary.getRiskName());
				newSummary.setRiskType(summary.getRiskType());
				newSummary.setModifiedTime(new Date());
				this.policySummaryDao.updatePolicySummary(newSummary);
			}
		}
	}	
}
