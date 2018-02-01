package com.imi.model.excel;

import java.math.BigDecimal;

import com.imi.base.annotate.ReportColumn;

/**
 * 集装箱保险
 * @author Devon
 *
 */
public class Risk08CTMessageExcel {
	@ReportColumn(columnName="保单号",columnOrder=1)
	private String policyNo;//上报的保单号
	@ReportColumn(columnName="保险人名称",columnOrder=2)
	private String appntName;//保险人名称
	@ReportColumn(columnName="被保险名称",columnOrder=3)
	private String insuredName;//被保险名称
	@ReportColumn(columnName="保险标的",columnOrder=4)
	private String insuredMark;//保险标的
	@ReportColumn(columnName="标的数量",columnOrder=5)
	private BigDecimal insuredMarkQuantity;//标的数量
	@ReportColumn(columnName="总保险金额",columnOrder=6)
	private BigDecimal amount;//总保险金额
	@ReportColumn(columnName="保险费",columnOrder=7)
	private BigDecimal prem;//保险费
	@ReportColumn(columnName="币种",columnOrder=8)
	private String currency;//币种
	@ReportColumn(columnName="主条款",columnOrder=9)
	private String mainTerm;//主条款
	@ReportColumn(columnName="附加条款",columnOrder=10)
	private String addTerm;//附加条款
	@ReportColumn(columnName="起保时间",converter = true,columnOrder=11)
	private String policyStartTime;//保险起始时间
	@ReportColumn(columnName="终保时间",converter = true,columnOrder=12)
	private String policyEndTime;//保险结束时间
	@ReportColumn(columnName="使用范围",columnOrder=13)
	private String useLimit;//使用范围
	@ReportColumn(columnName="承保区域",columnOrder=14)
	private String underwriteArea;//承保区域
	@ReportColumn(columnName="总保险价值",columnOrder=15)
	private String policyValue;//保险价值
	@ReportColumn(columnName="总费率",columnOrder=16)
	private String policyRate_08CT;//总保险费率（%）
	@ReportColumn(columnName="免赔额/率",columnOrder=17)
	private String franchise;//责任免赔额_08CT
	@ReportColumn(columnName="付费方式",columnOrder=18)
	private String payType;//付费方法
	@ReportColumn(columnName="特别约定",columnOrder=19)
	private String specialAgreement;//特别约定

	//日期展示转换
	public String convertPolicyStartTime(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		if(v.length()<8){
			return v;
		}else{
			return v.substring(0, 4)+"/"+v.substring(4, 6)+"/"+v.substring(6, 8);
		}
	}
	//日期展示转换
	public String convertPolicyEndTime(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		if(v.length()<8){
			return v;
		}else{
			return v.substring(0, 4)+"/"+v.substring(4, 6)+"/"+v.substring(6, 8);
		}
	}
	
}
