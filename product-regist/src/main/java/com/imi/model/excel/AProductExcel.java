package com.imi.model.excel;

import com.imi.base.annotate.ReportColumn;
import com.imi.entity.products.Product;
import com.imi.support.freemarker.DictContext;

public class AProductExcel{
	
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
	
	@ReportColumn(columnName="险类", converter=true,columnOrder=7)
	private String riskType;
	
	@ReportColumn(columnName="险种名称", converter=true,columnOrder=8)
	private String riskName;
	
	@ReportColumn(columnName="注册时间",columnOrder=9)
	private String registTime;
	
	@ReportColumn(columnName="产品状态",converter = true,columnOrder=10)
	private String status;

	@ReportColumn(columnName = "附加险",converter = true,columnOrder = 11)
	private String addSafe;

	@ReportColumn(columnName = "主险",converter = true,columnOrder = 12)
	private String mainSafe;

	@ReportColumn(columnName = "注销时间",converter = true,columnOrder = 13)
	private String modifiedTime;
	
	@ReportColumn(columnName="是否使用条款保护期",converter = true,columnOrder=14)
	private String protectionType;
	/*
	@ReportColumn(columnName="剩余保护期",columnOrder=2)
	private String protectionType;*/
	
	@ReportColumn(columnName="法律责任人",columnOrder=15)
	private String user_legalPerson;

	@ReportColumn(columnName="联系人",columnOrder=16)
	private String contactPersonName;

	@ReportColumn(columnName="联系人电话",columnOrder=17)
	private String contactPersonPhone;

	@ReportColumn(columnName="联系人邮箱",columnOrder=18)
	private String contactPersonEmail;
	
	@ReportColumn(columnName="外文条款注册原因",converter = true,columnOrder=19)
	private String proRegReason;


	public String convertAddSafe(Object entitie,String v){
		if (null == v || "".equals(v) || "null".equals(v)||"0".equals(v)) {
			return "";
		}
		return v;
	}
	public String convertMainSafe(Object entitie,String v){
		if (null == v || "".equals(v) || "null".equals(v)||"0".equals(v)) {
			return "";
		}
		return v;
	}
	public String convertModifiedTime(Object entitie,String v){
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}

	//对于空的数据展示为空字符串，而不是null
	public String convertProRegReason(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		return v;
	}
	
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
	
	public String convertStatus(Object entitie, String v) {
		if ("1".equals(v)) {
			return "正常";
		}
		if ("2".equals(v)) {
			return "待核查";
		}
		if ("3".equals(v)) {
			return "核查中";
		}
		if ("4".equals(v)) {
			return "注销";
		}
		return v;
	}
	
	public String convertProtectionType(Object entitie, String v) {
		if ("1".equals(v)) {
			return "有";
		}
		if ("2".equals(v)) {
			return "没有";
		}
		return v;
	}
	
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

	public String convertRiskName(Object entitie, String v) {
		  Product pro= (Product)entitie;
		  try{
		if (v != null) {
			return DictContext.getDictKV(pro.getRiskType(), v);
		}	
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		return v;
		
	
	}

}
