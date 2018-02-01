package com.imi.model.excel;

import java.math.BigDecimal;

import com.imi.base.annotate.ReportColumn;

/**
 * 保单汇总信息
 * @author Devon
 *
 */
public class PolicySummaryExcel {
	@ReportColumn(columnName="保险公司",columnOrder=1)
	private String reportUnit;//保险公司
	@ReportColumn(columnName="险种",columnOrder=2)
	private String riskName;//险种
	@ReportColumn(columnName="年份",columnOrder=3)
	private String reportYear;//汇总信息中的年份
	@ReportColumn(columnName="月份",columnOrder=4)
	private String reportMonth;//汇总信息中的月份
	@ReportColumn(columnName="保费-美元",columnOrder=5)
	private BigDecimal premUSD;//保费-美元
	@ReportColumn(columnName="保费-日元",columnOrder=6)
	private BigDecimal premJPY;//保费-日元
	@ReportColumn(columnName="保费-欧元",columnOrder=7)
	private BigDecimal premEUR;//保费-欧元
	@ReportColumn(columnName="保费-英镑",columnOrder=8)
	private BigDecimal premGBP;//保费-英镑
	@ReportColumn(columnName="保费-法郎",columnOrder=9)
	private BigDecimal premCHF;//保费-法郎
	@ReportColumn(columnName="折币对应的保费",columnOrder=10)
	private BigDecimal prem;//折币对应的保费
	
	//对于空的数据展示为空字符串，而不是null
		public String convertProRegReason(Object entitie, String v) {
			if (null == v || "".equals(v) || "null".equals(v)) {
				return "";
			}
			return v;
		}
}
