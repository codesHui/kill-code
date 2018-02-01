package com.imi.controllers.icrm.policy.constant;


public enum SUMMARY_ENUM {
	
	RiskName(0,"RiskName"), 
	ReportYear(1,"ReportYear"), 
	ReportMonth(2,"ReportMonth"), 
	PremUSD(3,"PremUSD"), 
	PremJPY(4,"PremJPY"), 
	PremEUR(5,"PremEUR"), 
	PremGBP(6,"PremGBP"), 
	PremCHF(7,"PremCHF"), 
	Prem(8,"Prem"); 
	
	private int index;
	private String property;
	
	public int getIndex() {
		return index;
	}

	public String getProperty() {
		return property;
	}

	SUMMARY_ENUM(int index,String property){
		this.index = index;
		this.property = property;
	}
	
	 public static String getPropertyByIndex(int index) {

	        for (SUMMARY_ENUM risk08OV : SUMMARY_ENUM.values()) {
	            if (risk08OV.getIndex() == index) {
	                return risk08OV.getProperty();
	            }
	        }
	        return "错误";
	    }
	
}
