package com.imi.controllers.icrm.policy.constant;

import java.math.BigDecimal;


public enum Risk_05OP_ENUM {

	PolicyNo(0,"PolicyNo"),//保单号
	AppntName(1,"AppntName"),//保险人名称
	InsuredName(2,"InsuredName"),//被保险人名称
	VesselsName(3,"VesselsName"),//船舶名称
	VesselsType(4,"VesselsType"),//船舶种类
	ProduceTime(5,"ProduceTime"),//建造年份
	TotalTonnage(6,"TotalTonnage"),//总吨位
	TradingLimit(7,"TradingLimit"),//航行区域
	AccidentLimit(8,"AccidentLimit"),//每次事故责任限额
	Franchise_05OP(9,"Franchise_05OP"),//每次事故责任免赔额
	Prem(10,"Prem"),//保险费
	Currency(11,"Currency"),//币种
	MainTerm(12,"MainTerm"),//主条款
	VesselsNo(13,"VesselsNo"),//船舶登记号IMO
	VesselsPort(14,"VesselsPort"),//船港籍
	PolicyStartTime(15,"PolicyStartTime"),//起保日期
	PolicyEndTime(16,"PolicyEndTime"),//终保日期
	VesselsLevel(17,"VesselsLevel"),//船级
	VesselsUse(18,"VesselsUse"),//船舶用途
	Producer(19,"Producer"),//制造厂商
	TotalAccidentLimit_05OP(20,"TotalAccidentLimit_05OP"),//累计责任限额
	AddTerm(21,"AddTerm"),//附加条款
	SpecialAgreement(22,"SpecialAgreement");//特别约定

	private int index;
	private String property;
	
	public int getIndex() {
		return index;
	}

	public String getProperty() {
		return property;
	}

	Risk_05OP_ENUM(int index,String property){
		this.index = index;
		this.property = property;
	}
	
	 public static String getPropertyByIndex(int index) {

	        for (Risk_05OP_ENUM risk08OV : Risk_05OP_ENUM.values()) {
	            if (risk08OV.getIndex() == index) {
	                return risk08OV.getProperty();
	            }
	        }
	        return "错误";
	    }
	
	
}
