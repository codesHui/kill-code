package com.imi.controllers.icrm.policy.constant;

public enum Risk_08CT_ENUM {

	PolicyNo(0,"PolicyNo"),//保单号
	AppntName(1,"AppntName"),//保险人名称
	InsuredName(2,"InsuredName"),//被保险人名称
	InsuredMark(3,"InsuredMark"),//保险标的
	InsuredMarkQuantity(4,"InsuredMarkQuantity"),//保险标的数量
	Amount(5,"Amount"),//保险金额
	Prem(6,"Prem"),//保险费
	Currency(7,"Currency"),//币种
	MainTerm(8,"MainTerm"),//主条款
	AddTerm(9,"AddTerm"),//附加条款
	PolicyStartTime(10,"PolicyStartTime"),//起保日期
	PolicyEndTime(11,"PolicyEndTime"),//终保日期
	UseLimit(12,"UseLimit"),//使用范围
	UnderwriteArea(13,"UnderwriteArea"),//承保区域
	PolicyValue(14,"PolicyValue"),//保险价值
	PolicyRate_08CT(15,"PolicyRate_08CT"),//保险费率
	Franchise(16,"Franchise"),//免赔率
	PayType(17,"PayType"),//付费方式
	SpecialAgreement(18,"SpecialAgreement");//特别约定

	
	private int index;
	private String property;
	
	public int getIndex() {
		return index;
	}

	public String getProperty() {
		return property;
	}

	Risk_08CT_ENUM(int index,String property){
		this.index = index;
		this.property = property;
	}
	
	 public static String getPropertyByIndex(int index) {

	        for (Risk_08CT_ENUM risk08OV : Risk_08CT_ENUM.values()) {
	            if (risk08OV.getIndex() == index) {
	                return risk08OV.getProperty();
	            }
	        }
	        return "错误";
	    }
	
	
}
