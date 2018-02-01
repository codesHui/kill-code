package com.imi.entity.report;

public class ProductRegistQuarter {
	
	// 注册人编号
	private String registeredCode;
	// 公司名称
	private String companyName;
	
	// 复查致核查
	private int fk;
	// 反馈致核查
	private int ts;
	// 复议
	private int fy;
	// 强制注销
	private int zx;
	public String getRegisteredCode() {
		return registeredCode;
	}
	public void setRegisteredCode(String registeredCode) {
		this.registeredCode = registeredCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getFk() {
		return fk;
	}
	public void setFk(int fk) {
		this.fk = fk;
	}
	public int getTs() {
		return ts;
	}
	public void setTs(int ts) {
		this.ts = ts;
	}
	public int getFy() {
		return fy;
	}
	public void setFy(int fy) {
		this.fy = fy;
	}
	public int getZx() {
		return zx;
	}
	public void setZx(int zx) {
		this.zx = zx;
	}
	public ProductRegistQuarter(String registeredCode) {
		super();
		this.registeredCode = registeredCode;
	}
	
	public ProductRegistQuarter() {
	}
	
	
	
	
	
	
	
	
	

}
