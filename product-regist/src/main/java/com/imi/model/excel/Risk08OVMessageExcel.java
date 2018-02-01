package com.imi.model.excel;

import java.math.BigDecimal;

import com.imi.base.annotate.ReportColumn;

/**
 * 远洋船舶险
 * @author Devon
 *
 */
public class Risk08OVMessageExcel {
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
	@ReportColumn(columnName="船级",columnOrder=8)
	private String vesselsLevel;//船级
	@ReportColumn(columnName="保险金额",columnOrder=9)
	private BigDecimal amount;//总保险金额
	@ReportColumn(columnName="航行区域",columnOrder=10)
	private String tradingLimit;//航行区域
	@ReportColumn(columnName="承保险别（主条款）",columnOrder=11)
	private String mainTerm;//保险主条款
	@ReportColumn(columnName="免赔额",columnOrder=12)
	private String franchise;//责任免赔额
	@ReportColumn(columnName="保险费率(%)",columnOrder=13)
	private BigDecimal policyRate;//总保险费率（%）
	@ReportColumn(columnName="保费",columnOrder=14)
	private BigDecimal prem;//保费
	@ReportColumn(columnName="币种",columnOrder=15)
	private String currency;//币种
	@ReportColumn(columnName="附加险",columnOrder=16)
	private String addRiskName;//附加险
	@ReportColumn(columnName="附加险保费",columnOrder=17)
	private BigDecimal addPrem;//附加险保费
	@ReportColumn(columnName="司法管辖地",columnOrder=18)
	private String judicialControl;//司法管辖地
	@ReportColumn(columnName="起保时间",converter = true,columnOrder=19)
	private String policyStartTime;//保险起始时间
	@ReportColumn(columnName="终保时间",converter = true,columnOrder=20)
	private String policyEndTime;//保险结束时间
	@ReportColumn(columnName="境外船舶险",columnOrder=21)
	private String isOffshoreVessels;//境外船舶险
	@ReportColumn(columnName="船舶登记号IMO",columnOrder=22)
	private String vesselsNo;//船舶登记号IMO
	@ReportColumn(columnName="建造商",columnOrder=23)
	private String producer;//建造商
	@ReportColumn(columnName="保险价值",columnOrder=24)
	private String policyValue;//保险价值
	@ReportColumn(columnName="船壳险保险金额",columnOrder=25)
	private String vesselsAmount;//船壳险保险金额
	@ReportColumn(columnName="船壳险费率",columnOrder=26)
	private String vesselsRate;//船壳险费率
	@ReportColumn(columnName="特别约定",columnOrder=27)
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
