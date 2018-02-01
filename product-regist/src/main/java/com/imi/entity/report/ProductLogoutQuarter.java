package com.imi.entity.report;

/**
 * 注销事由统计
 * @author xugang
 * 
 * 产品注销类别
 *  1/条款停止使用
	2/修改保险责任
	3/修改除外责任
	4/注册产品条件有误
	5/注册条款中有错别字
	6/注册时操作失误
	7/其他
	8/强制注销
 */
public class ProductLogoutQuarter {
	
	private String registeredCode;
	private String companyName;
	
	private int c1;
	private int c2;
	private int c3;
	private int c4;
	private int c5;
	private int c6;
	private int c7;
	private int c8;
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
	public int getC1() {
		return c1;
	}
	public void setC1(int c1) {
		this.c1 = c1;
	}
	public int getC2() {
		return c2;
	}
	public void setC2(int c2) {
		this.c2 = c2;
	}
	public int getC3() {
		return c3;
	}
	public void setC3(int c3) {
		this.c3 = c3;
	}
	public int getC4() {
		return c4;
	}
	public void setC4(int c4) {
		this.c4 = c4;
	}
	public int getC5() {
		return c5;
	}
	public void setC5(int c5) {
		this.c5 = c5;
	}
	public int getC6() {
		return c6;
	}
	public void setC6(int c6) {
		this.c6 = c6;
	}
	public int getC7() {
		return c7;
	}
	public void setC7(int c7) {
		this.c7 = c7;
	}
	public int getC8() {
		return c8;
	}
	public void setC8(int c8) {
		this.c8 = c8;
	}
	
	
	
	
	
	
	

}
