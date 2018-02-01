package com.imi.controllers.icrm.policy.constant;


public enum Risk_08OV_ENUM {

	PolicyNo(0,"PolicyNo"),//保单号
	AppntName(1,"AppntName"),//保险人名称
	InsuredName(2,"InsuredName"),//被保险人名称
	VesselsName(3,"VesselsName"),//船舶名称
	VesselsType(4,"VesselsType"),//船舶种类
	ProduceTime(5,"ProduceTime"),//建造年份
	TotalTonnage(6,"TotalTonnage"),//总吨位
	VesselsLevel(7,"VesselsLevel"),//船级
	Amount(8,"Amount"),//保险金额
	TradingLimit(9,"TradingLimit"),//航行区域
	MainTerm(10,"MainTerm"),//主条款
	Franchise(11,"Franchise"),//责任免赔额
	PolicyRate(12,"PolicyRate"),//保险费率
	Prem(13,"Prem"),//保险费
	Currency(14,"Currency"),//币种
	AddRiskName(15,"AddRiskName"),//附加险
	AddPrem(16,"AddPrem"),//附加险保费
	JudicialControl(17,"JudicialControl"),//司法管辖地
	PolicyStartTime(18,"PolicyStartTime"),//起保日期
	PolicyEndTime(19,"PolicyEndTime"),//终保日期
	IsOffshoreVessels(20,"IsOffshoreVessels"),//境外船舶险(是/否)
	VesselsNo(21,"VesselsNo"),//船舶登记号IMO
	Producer(22,"Producer"),//建造商
	PolicyValue(23,"PolicyValue"),//保险价值
	VesselsAmount(24,"VesselsAmount"),//船壳保险金额
	VesselsRate(25,"VesselsRate"),//船壳险费率
	SpecialAgreement(26,"SpecialAgreement");//特别约定
	
	private int index;
	private String property;
	
	public int getIndex() {
		return index;
	}

	public String getProperty() {
		return property;
	}

	Risk_08OV_ENUM(int index,String property){
		this.index = index;
		this.property = property;
	}
	
	 public static String getPropertyByIndex(int index) {

	        for (Risk_08OV_ENUM risk08OV : Risk_08OV_ENUM.values()) {
	            if (risk08OV.getIndex() == index) {
	                return risk08OV.getProperty();
	            }
	        }
	        return "错误";
	    }
	
	
}
