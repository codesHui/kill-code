package com.imi.entity.report;
/**
 * 季度报表
 * @author xugang
 *
 */
public class ProductQuarter {
	
	//险类名称
	private String riskType;
	// 险种名称
	private String riskName;
	
	// 产品种类
	private String productType;
	
	// 第01年1季度
	private int quarter0101;
	// 第01年2季度
	private int quarter0102;
	// 第01年3季度
	private int quarter0103;
	// 第01年4季度
	private int quarter0104;
	// 第01小计
	private int quarter01total;
	
	private int quarter0201;
	private int quarter0202;
	private int quarter0203;
	private int quarter0204;
	private int quarter02total;
	
	private int quarter0301;
	private int quarter0302;
	private int quarter0303;
	private int quarter0304;
	private int quarter03total;
	
	private int quarterTotal;
	
	
	public int getQuarter0101() {
		return quarter0101;
	}
	public void setQuarter0101(int quarter0101) {
		this.quarter0101 = quarter0101;
	}
	public int getQuarter0102() {
		return quarter0102;
	}
	public void setQuarter0102(int quarter0102) {
		this.quarter0102 = quarter0102;
	}
	public int getQuarter0103() {
		return quarter0103;
	}
	public void setQuarter0103(int quarter0103) {
		this.quarter0103 = quarter0103;
	}
	public int getQuarter0104() {
		return quarter0104;
	}
	public void setQuarter0104(int quarter0104) {
		this.quarter0104 = quarter0104;
	}
	public int getQuarter01total() {
		return quarter01total;
	}
	public void setQuarter01total(int quarter01total) {
		this.quarter01total = quarter01total;
	}
	public int getQuarter0201() {
		return quarter0201;
	}
	public void setQuarter0201(int quarter0201) {
		this.quarter0201 = quarter0201;
	}
	public int getQuarter0202() {
		return quarter0202;
	}
	public void setQuarter0202(int quarter0202) {
		this.quarter0202 = quarter0202;
	}
	public int getQuarter0203() {
		return quarter0203;
	}
	public void setQuarter0203(int quarter0203) {
		this.quarter0203 = quarter0203;
	}
	public int getQuarter0204() {
		return quarter0204;
	}
	public void setQuarter0204(int quarter0204) {
		this.quarter0204 = quarter0204;
	}
	public int getQuarter02total() {
		return quarter02total;
	}
	public void setQuarter02total(int quarter02total) {
		this.quarter02total = quarter02total;
	}
	public int getQuarter0301() {
		return quarter0301;
	}
	public void setQuarter0301(int quarter0301) {
		this.quarter0301 = quarter0301;
	}
	public int getQuarter0302() {
		return quarter0302;
	}
	public void setQuarter0302(int quarter0302) {
		this.quarter0302 = quarter0302;
	}
	public int getQuarter0303() {
		return quarter0303;
	}
	public void setQuarter0303(int quarter0303) {
		this.quarter0303 = quarter0303;
	}
	public int getQuarter0304() {
		return quarter0304;
	}
	public void setQuarter0304(int quarter0304) {
		this.quarter0304 = quarter0304;
	}
	public int getQuarter03total() {
		return quarter03total;
	}
	public void setQuarter03total(int quarter03total) {
		this.quarter03total = quarter03total;
	}
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getQuarterTotal() {
		return quarterTotal;
	}
	public void setQuarterTotal(int quarterTotal) {
		this.quarterTotal = quarterTotal;
	}
	public ProductQuarter(String productType) {
		super();
		this.productType = productType;
	}
	
	public ProductQuarter() {
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
