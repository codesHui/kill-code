package com.imi.controllers.icrm.policy.constant;

import java.math.BigDecimal;


public enum Risk_09IE_ENUM {

	PolicyNo(0,"PolicyNo"),//币种
	AppntName(1,"AppntName"),//保险人名称
	InsuredName(2,"InsuredName"),//被保险人名称
	Currency(3,"Currency"),//币种
	Amount(4,"Amount"),//保险金额
	Prem(5,"Prem"),//保险费
	SailingDate(6,"SailingDate"),//总吨位
	LoadingName(7,"LoadingName"),//船级
	StartPlace(8,"StartPlace"),//起运地
	EndPlace(9,"EndPlace"),//目的地
	MainTerm(10,"MainTerm"),//主条款
	AddTerm(11,"AddTerm"),//附加条款
	ClaimPlace(12,"ClaimPlace"),//赔付地
	BillNo(13,"BillNo"),//提单号
	ContractNo(14,"ContractNo"),//发票号
	GoodsQuantity(15,"GoodsQuantity"),//包装与数量
	GoodsName(16,"GoodsName"),//保险货物名称
	InvoiceAmount(17,"InvoiceAmount"),//发票金额
	Franchise(18,"Franchise"),//免赔额
	ShippingType(19,"ShippingType"),//运输方式
	SpecialAgreement(20,"SpecialAgreement"),//特别约定
	VesselsAge(21,"VesselsAge"),//船龄
	PolicyStartTime(22,"PolicyStartTime"),//起保日期
	PolicyEndTime(23,"PolicyEndTime");//终保日期
	
	private int index;
	private String property;
	
	public int getIndex() {
		return index;
	}

	public String getProperty() {
		return property;
	}

	Risk_09IE_ENUM(int index,String property){
		this.index = index;
		this.property = property;
	}
	
	 public static String getPropertyByIndex(int index) {

	        for (Risk_09IE_ENUM risk08OV : Risk_09IE_ENUM.values()) {
	            if (risk08OV.getIndex() == index) {
	                return risk08OV.getProperty();
	            }
	        }
	        return "错误";
	    }
	
	
}
