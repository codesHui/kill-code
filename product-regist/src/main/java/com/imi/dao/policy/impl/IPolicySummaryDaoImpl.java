package com.imi.dao.policy.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.policy.IPolicySummaryDao;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.reportPolicy.ReportSummary;
import com.imi.model.policy.PolicyInformation;
import com.imi.model.policy.PolicySummaryInfo;
import com.imi.model.policy.SummaryVo;
import com.imi.model.report.ReportVo;
import com.softvan.utils.StringUtil;
@Repository
public class IPolicySummaryDaoImpl extends BaseDaoImpl<ReportSummary>  implements IPolicySummaryDao {

	@Resource
	private JdbcTemplate  jdbcTemplate;
	
	/**
	 * 查询biz_policy_summary表内容
	 * @return
	 */
	private String sql_sum_content(){
		StringBuffer sql = new StringBuffer();
		sql.append("select p from ReportSummary p where 1=1 ");
		return sql.toString();
	}
	
	/**
	 * 查询汇总查询SQL条件
	 * @param info
	 * @return
	 */
	private String sql_sum_condition(PolicySummaryInfo info){
		StringBuffer sql = new StringBuffer();
		//险种类型
		if(StringUtil.isNotNull(info.getRiskType())){
			sql.append(" and p.riskType = '").append(info.getRiskType()).append("'");
		}		
		//年份
		if(StringUtil.isNotNull(info.getReportYear())){
			sql.append(" and p.reportYear = '").append(info.getReportYear()).append("'");
		}
		//月份
		if(StringUtil.isNotNull(info.getReportMonth())){
			sql.append(" and p.reportMonth = '").append(info.getReportMonth()).append("'");
		}
		//登记会员单位
		if(StringUtil.isNotNull(info.getReportUnit())){
			sql.append(" and p.reportUnit = '").append(info.getReportUnit()).append("'");
		}
		return sql.toString();
	}
	
	
	@Override
	public Long insertPolicySummary(ReportSummary summary) {
		if(summary == null) {
			return null;
		}
		return (Long)this.save(summary);
	}
	
	@Override
	public void updatePolicySummary(ReportSummary summary) {
		// TODO Auto-generated method stub
		super.update(summary);
	}
	@Override
	public ReportSummary qryReportSymmary(PolicySummaryInfo sumInfo) {
		
		String hql = this.sql_sum_content() +  this.sql_sum_condition(sumInfo);
		System.out.println("查询SQL：" + hql);
		ReportSummary reportSummary = (ReportSummary)this.uniqueResult(hql, null);
		return reportSummary;
	}

	@Override
	public List<SummaryVo> queryList(Map condition) {

		Map<String, ReportVo> map = Maps.newHashMap();

		List param = new ArrayList();
		StringBuffer sbf = new StringBuffer("select * from ( SELECT  ");
		StringBuffer commonsql = commonsql();
		// 拼接条件
		//险种
		if(condition.containsKey("riskType")&&StringUtils.isNotEmpty((String) condition.get("riskType"))  ){
			 commonsql.append("and t.risk_type='").append((String) condition.get("riskType")).append("' ");
		}
		//上报单位
		if(condition.containsKey("reportUnit")&&StringUtils.isNotEmpty((String) condition.get("reportUnit"))  ){
			 commonsql.append("and t.report_unit='").append((String) condition.get("reportUnit")).append("' ");
		}
		//上报开始时间
		if(condition.containsKey("summaryStartTime")&&StringUtils.isNotEmpty((String) condition.get("summaryStartTime"))  ){
			 String summaryStartTime = (String) condition.get("summaryStartTime");
			 summaryStartTime = summaryStartTime.substring(0, 4) + summaryStartTime.substring(5, 7);
			 
			 commonsql.append(" and case when t.report_month < 10 then CONCAT(t.report_year,0,t.report_month)");
			 commonsql.append(" else CONCAT(t.report_year,t.report_month) end >= ").append(summaryStartTime);
		}
		//上报结束时间
		if(condition.containsKey("summaryEndTime")&&StringUtils.isNotEmpty((String) condition.get("summaryEndTime"))  ){

			 String summaryEndTime = (String) condition.get("summaryEndTime");
			 summaryEndTime = summaryEndTime.substring(0, 4) + summaryEndTime.substring(5, 7);
			 
			 commonsql.append(" and case when t.report_month < 10 then CONCAT(t.report_year,0,t.report_month)");
			 commonsql.append(" else CONCAT(t.report_year,t.report_month) end <= ").append(summaryEndTime);
		}
		
		sbf.append(" (SELECT b.companyName FROM  biz_member_user  b where b.registeredCode=t.report_unit) AS reportUnit ");
		sbf.append(",t.risk_name as riskName"); 
		sbf.append(",t.report_year as reportYear");  
		sbf.append(",t.report_month as reportMonth"); 
		sbf.append(commonsql);
		sbf.append(" GROUP BY t.report_unit ,t.risk_type,t.report_year,t.report_month order by t.id desc) g");
		sbf.append(" UNION ALL (SELECT '合计'  AS reportUnit ");
		sbf.append(",'' as riskName"); 
		sbf.append(",'' as reportYear");  
		sbf.append(",'' as reportMonth"); 
		sbf.append(commonsql).append(")");

		return querySqlByJdbcTpl(sbf.toString(), param, new RowMapper() {

			public SummaryVo mapRow(ResultSet rs, int arg1) throws SQLException {
				SummaryVo model = new SummaryVo();
				model.setReportUnit(rs.getNString("reportUnit"));
				model.setRiskName(rs.getNString("riskName"));
				model.setReportYear(rs.getNString("reportYear"));
				model.setReportMonth(rs.getNString("reportMonth"));
				model.setPremUSD(rs.getBigDecimal("premUSD"));
				model.setPremJPY(rs.getBigDecimal("premJPY"));
				model.setPremEUR(rs.getBigDecimal("premEUR"));
				model.setPremGBP(rs.getBigDecimal("premGBP"));
				model.setPremCHF(rs.getBigDecimal("premCHF"));
				model.setPrem(rs.getBigDecimal("prem"));

				return model;
			}
		});
	}
	@Override
	public void queryListForPage(PolicySummaryInfo info) {

		String hql = this.sql_content() +  this.sql_condition(info);
		List<ReportSummary> cList =this.find(hql);;

		List<ReportSummary> dlist = new ArrayList<ReportSummary>();
		/*List<ReportSummary> list = this.find(hql, null, info.getPage(), info.getRows());*/
		List<ReportSummary> list = new ArrayList<ReportSummary>();
		//只选择起始时间如果选择起始时间如果结果年份等于选择年份比较月份
		System.out.println(cList.size()+"cccccc");
		
		if(!StringUtils.isBlank(info.getSummaryStartTime()) && StringUtils.isBlank(info.getSummaryEndTime())){
			for(int i=0;i<cList.size();i++){
				if(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear())){
					if(Integer.parseInt(cList.get(i).getReportMonth())<Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),5,7))){
						System.out.println(cList.get(i));
						dlist.add(cList.get(i));
						
					}
				}
			}
			System.out.println(cList.size()+"aaaa");
		}
		//只选择了结束时间
		if(StringUtils.isBlank(info.getSummaryStartTime()) && !StringUtils.isBlank(info.getSummaryEndTime())){
			for(int i=0;i<cList.size();i++){
				if(Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear())){
					if(Integer.parseInt(cList.get(i).getReportMonth())>Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),5,7))){
						dlist.add(cList.get(i));
					}
				}
			}
		}
		//起始与结束时间都选了
		if(!StringUtils.isBlank(info.getSummaryStartTime()) && !StringUtils.isBlank(info.getSummaryEndTime())){
			for(int i=0;i<cList.size();i++){
				//与起始时间是一年，不与结束时间一年
				if(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear()) && Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))>Integer.parseInt(cList.get(i).getReportYear())){
					if(Integer.parseInt(cList.get(i).getReportMonth())<Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),5,7))){
						dlist.add(cList.get(i));
						continue;
					}
				}
				//与起始时间和结束时间都是一年
				if(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear()) && Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear())){
					System.out.println(Integer.parseInt(cList.get(i).getReportMonth()));
					if((Integer.parseInt(cList.get(i).getReportMonth())<Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),5,7))) || (Integer.parseInt(cList.get(i).getReportMonth())>Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),5,7)))){
						dlist.add(cList.get(i));
						continue;
					}
				}
				//与结束时间是一年,并且开始时间与结束时间不是一年
				if(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4))<Integer.parseInt(cList.get(i).getReportYear()) && Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear()) && Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))!=Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4)) ){
					if(Integer.parseInt(cList.get(i).getReportMonth())>Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),5,7))){
						dlist.add(cList.get(i));
						continue;
					}
				}
				//与开始时间是一年,并且开始时间与结束时间不是一年
				if(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4))==Integer.parseInt(cList.get(i).getReportYear()) && Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))!=Integer.parseInt(cList.get(i).getReportYear()) && Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),0, 4))!=Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),0, 4)) ){
					if(Integer.parseInt(cList.get(i).getReportMonth())<Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),5,7))){
						dlist.add(cList.get(i));
						continue;
					}
				}
			}
		}
		//对比两个list过滤相同的对象
		cList.removeAll(dlist);
		info.setTotal(cList == null ? String.valueOf(0) : String.valueOf(cList.size()));
		System.out.println(cList);
		System.out.println(cList.size()+"ddddddd");
		int count=0;
		if(info.getPage()==1){
			
		}else{
			count=(info.getPage()-1)*10;
		}
		for(int j=count;j<cList.size();j++){
			//如果
			if(j<info.getPage()*10  ){
				System.out.println(cList.get(j));
				list.add(cList.get(j));
			}
		}
		//赋值分页数据
		info.setList(list);
		
	}
	/**
	 * 查询biz_policy_summary表内容
	 * @return
	 */
	private String sql_content(){
		StringBuffer sql = new StringBuffer();
		sql.append("select p from ReportSummary p where 1=1 ");
		return sql.toString();
	}

	/**
	 * 查询SQL条件
	 * @param info
	 * @return
	 */
	private String sql_condition(PolicySummaryInfo info){
		
		StringBuffer sql = new StringBuffer();
		////险种
		if(!StringUtils.isBlank(info.getRiskType())){
			sql.append(" and p.riskType  = '").append(StringUtils.trim(info.getRiskType())).append("' ");
		}
		//上报单位
		if(!StringUtils.isBlank(info.getReportUnit())){
			sql.append(" and p.reportUnit = '").append(info.getReportUnit()).append("' ");
		}
		//上报开始时间
		if(!StringUtils.isBlank(info.getSummaryStartTime())){
			sql.append(" and p.reportYear >= '").append(StringUtils.substring(info.getSummaryStartTime(),0, 4)).append("' ");
			/*if(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),5,7))>10){
				sql.append(" and p.reportMonth >= '").append(StringUtils.substring(info.getSummaryEndTime(),5, 7)).append("' ");
			}else{
				sql.append(" and p.reportMonth >= '").append(Integer.parseInt(StringUtils.substring(info.getSummaryStartTime(),5, 7).toString())).append("' ");
			}*/
		}
		//上报结束时间
		if(!StringUtils.isBlank(info.getSummaryEndTime())){
			sql.append(" and p.reportYear <= '").append(StringUtils.substring(info.getSummaryEndTime(),0, 4)).append("' ");
			/*if(Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),5,7))>=10){
				sql.append(" and p.reportMonth <= '").append(StringUtils.substring(info.getSummaryEndTime(),5, 7)).append("' ");
			}else{
				sql.append(" and p.reportMonth <= '").append(Integer.parseInt(StringUtils.substring(info.getSummaryEndTime(),5, 7).toString())).append("' ");
			}*/
		}

		sql.append(" order by p.id desc ");
		
		return sql.toString();
	}
	private StringBuffer commonsql(){
		 StringBuffer sbf = new StringBuffer();
  
		 sbf.append(",sum(t.prem_usd) as premUSD");// 
		 sbf.append(",sum(t.prem_jpy) AS premJPY");// 
		 sbf.append(",sum(t.prem_eur) AS premEUR");// 
		 sbf.append(",sum(t.prem_gbp) AS premGBP");// 
		 sbf.append(",sum(t.prem_chf) AS premCHF");// 
		 sbf.append(",sum(t.prem) AS prem");// 
		 sbf.append(" FROM biz_policy_summary t");
		 sbf.append(" WHERE 1=1 ");

		 return sbf;	
	}
	
	private StringBuffer commonSumsql(){
		 StringBuffer sbf = new StringBuffer();
 
		 sbf.append(",sum(t.prem_usd) as premUSD");// 
		 sbf.append(",sum(t.prem_jpy) AS premJPY");// 
		 sbf.append(",sum(t.prem_eur) AS premEUR");// 
		 sbf.append(",sum(t.prem_gbp) AS premGBP");// 
		 sbf.append(",sum(t.prem_chf) AS premCHF");// 
		 sbf.append(",sum(t.prem) AS prem");// 
		 sbf.append(" FROM biz_policy_summary t");
		 sbf.append(" WHERE 1=1 ");

		 return sbf;	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List querySqlByJdbcTpl(String sql, List<Object> params,
			RowMapper rowMapper) {
		List list = new ArrayList();
		if (rowMapper != null) {
			System.out.println("sql is :" + sql);
			list = jdbcTemplate.query(sql.toLowerCase(),
					params != null ? params.toArray() : null, rowMapper);
		} else {
			sql = sql.replace("NVL", "IFNULL");
			System.out.println("sql is :" + sql);
			list = jdbcTemplate.queryForList(sql.toLowerCase(),
					params.toArray());
		}
		return list;

	}

}
