package com.imi.model.excel;

import java.math.BigDecimal;

import com.imi.base.annotate.ReportColumn;

/**
 * 船舶油污责任险
 * @author Devon
 *
 */
public class Risk05OPMessageExcel {
	@ReportColumn(columnName="保单号",columnOrder=1)
	private String policyNo;//上报的保单号
	@ReportColumn(columnName="保险人名称",columnOrder=2)
	private String appntName;//保险人名称
	@ReportColumn(columnName="被保险名称",columnOrder=3)
	private String insuredName;//被保险名称
	@ReportColumn(columnName="船舶名称",columnOrder=4)
	private String vesselsName;//船舶名称
	@ReportColumn(columnName="船舶类型",columnOrder=5)
	private String vesselsType;//船舶类型
	@ReportColumn(columnName="建造年份",columnOrder=6)
	private String produceTime;//建造年份
	@ReportColumn(columnName="总吨位",columnOrder=7)
	private String totalTonnage;//总吨位
	@ReportColumn(columnName="航行区域",columnOrder=8)
	private String tradingLimit;//航行区域
	@ReportColumn(columnName="每次事故责任限额",columnOrder=9)
	private BigDecimal accidentLimit;//每次事故责任限额
	@ReportColumn(columnName="每次事故免赔额",columnOrder=10)
	private BigDecimal franchise_05OP;//每次事故免赔额
	@ReportColumn(columnName="保险费",columnOrder=11)
	private BigDecimal prem;//保险费
	@ReportColumn(columnName="币种",columnOrder=12)
	private String currency;//币种
	@ReportColumn(columnName="保险主条款",columnOrder=13)
	private String mainTerm;//保险主条款
	@ReportColumn(columnName="船舶识别号/编号",columnOrder=14)
	private String vesselsNo;//船舶识别号/编号
	@ReportColumn(columnName="船籍港",columnOrder=15)
	private String vesselsPort;//船籍港
	@ReportColumn(columnName="起保时间",converter = true,columnOrder=16)
	private String policyStartTime;//保险起始时间
	@ReportColumn(columnName="终保时间",converter = true,columnOrder=17)
	private String policyEndTime;//保险结束时间
	@ReportColumn(columnName="船级",columnOrder=18)
	private String vesselsLevel;//船级
	@ReportColumn(columnName="船舶用途",columnOrder=19)
	private String vesselsUse;//船舶用途
	@ReportColumn(columnName="制造厂家",columnOrder=20)
	private String producer;//建造商
	@ReportColumn(columnName="累计责任限额",columnOrder=21)
	private String totalAccidentLimit_05OP;//累计责任限额05OP
	@ReportColumn(columnName="保险附加条款",columnOrder=22)
	private String addTerm;//附加条款
	@ReportColumn(columnName="特别约定",columnOrder=23)
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
