package com.imi.model.excel;

import com.imi.base.annotate.ReportColumn;
import com.imi.base.model.reviews.ReviewsExcelInfo;
import com.imi.support.freemarker.DictContext;

public class RProductExcel {


	@ReportColumn(columnName="序号",columnOrder=1)
	private int productIndex;

	/*Product信息 Start*/
	@ReportColumn(columnName="产品注册号",columnOrder=1)
	private String productNO;

	@ReportColumn(columnName="产品名称",columnOrder=2)
	private String productName;
	
	@ReportColumn(columnName="产品注册人",columnOrder=3)
	private String registPerson;
	
	@ReportColumn(columnName="语言",columnOrder=4)
	private String productLanguage;
	
	@ReportColumn(columnName="中文名称",columnOrder=5)
	private String chineseName;
	
	@ReportColumn(columnName="类别",converter = true,columnOrder=6)
	private String productType;

	@ReportColumn(columnName="险类",converter = true,columnOrder=7)
	private String riskType;
	
	@ReportColumn(columnName="险种名称",converter = true,columnOrder=8)
	private String riskName;
	
	@ReportColumn(columnName="注册时间",columnOrder=9)
	private String registTime;
	/*Product信息 End*/

	@ReportColumn(columnName="复查意见",converter = true,columnOrder=10)
	private String flowOperate;

	@ReportColumn(columnName="复查描述",converter = true,columnOrder=11)
	private String flowReason;

	@ReportColumn(columnName="复查人",converter = true,columnOrder=12)
	private String flowMan;

	@ReportColumn(columnName="复查时间",converter = true,columnOrder=13)
	private String flowDate;

	@ReportColumn(columnName="抽查意见",converter = true,columnOrder=14)
	private String flowOperate3;//抽查意见

	@ReportColumn(columnName="抽查描述",converter = true,columnOrder=15)
	private String flowReason3;//抽查描述

	@ReportColumn(columnName="抽查人",converter = true,columnOrder=16)
	private String flowMan3;//抽查人

	@ReportColumn(columnName="抽查时间",converter = true,columnOrder=17)
	private String flowDate3;//抽查时间

	@ReportColumn(columnName = "整改通知时间",converter = true ,columnOrder = 18)
	private String changeTellDate;//整改通知时间

	@ReportColumn(columnName = "整改结果",converter = true ,columnOrder = 19)
	private String changeResult;//整改结果

	@ReportColumn(columnName = "整改完成时间",converter = true ,columnOrder = 20)
	private String changeEndDate;//整改完成时间

	/*public String convertModifiedTime(Object entitie,String v){
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}*/

	//序号判断
	public String convertOrderNo(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	//产品类别判断
	public String convertProductType(Object entitie, String v) {
		if ("1".equals(v)) {
			return "主险";
		}
		if ("2".equals(v)) {
			return "附加险";
		}
		if ("3".equals(v)) {
			return "其他";
		}
		return v;
	}

	//险类判断
	public String convertRiskType(Object entitie, String v) {
		try{
		if (v != null) {
			System.out.println(v);
			return DictContext.getDictKV("RISK", v);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}

	//险种名称信息
	public String convertRiskName(Object entitie, String v) {
		ReviewsExcelInfo pro= (ReviewsExcelInfo)entitie;
		try{
			if (v != null ) {
				return DictContext.getDictKV(pro.getReviews().getProduct().getRiskType(), v);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}

	//复查人意见
	public String convertFlowOperate(Object entitie, String v) {

		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	//复查人描述
	public String convertFlowReason(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	//复查人
	public String convertFlowMan(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	//复查人时间
	public String convertFlowDate(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	//=======================================
	public String convertFlowOperate3(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	public String convertFlowReason3(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	public String convertFlowMan3(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	public String convertFlowDate3(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	/*整改系列信息*/
	public String convertChangeTellDate(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	public String convertChangeResult(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	public String convertChangeEndDate(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}
}
