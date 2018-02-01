package com.imi.controllers.icrm.policy.constant;

import java.math.BigDecimal;


public enum Risk_05PI_ENUM {
	
	PolicyNo(0,"PolicyNo"),//保单号
	AppntName(1,"AppntName"),//保险人名称
	InsuredName(2,"InsuredName"),//被保险人名称
	VesselsName(3,"VesselsName"),//船舶名称
	VesselsType(4,"VesselsType"),//船舶种类
	ProduceTime(5,"ProduceTime"),//建造年份
	TotalTonnage(6,"TotalTonnage"),//总吨位
	TradingLimit(7,"TradingLimit"),//航行区域
	TotalAccidentLimit(8,"TotalAccidentLimit"),//累计责任限额
	Prem(9,"Prem"),//保险费
	Currency(10,"Currency"),//币种
	MainTerm(11,"MainTerm"),//主条款
	VesselsLevel(12,"VesselsLevel"),//船级
	VesselsNo(13,"VesselsNo"),//船舶登记号IMO
	VesselsPort(14,"VesselsPort"),//船港籍
	PolicyStartTime(15,"PolicyStartTime"),//起保日期
	PolicyEndTime(16,"PolicyEndTime"),//终保日期
	AccidentLimit_05PI(17,"AccidentLimit_05PI"),//每次事故责任限额
	Franchise(18,"Franchise"),//每次事故责任免赔额
	SpecialAgreement(19,"SpecialAgreement"),//特别约定
	AddTerm(20,"AddTerm");//保险附加条款
 
	private int index;
	private String property;
	
	public int getIndex() {
		return index;
	}

	public String getProperty() {
		return property;
	}

	Risk_05PI_ENUM(int index,String property){
		this.index = index;
		this.property = property;
	}
	
	 public static String getPropertyByIndex(int index) {

	        for (Risk_05PI_ENUM risk08OV : Risk_05PI_ENUM.values()) {
	            if (risk08OV.getIndex() == index) {
	                return risk08OV.getProperty();
	            }
	        }
	        return "错误";
	    }
	
	
}
