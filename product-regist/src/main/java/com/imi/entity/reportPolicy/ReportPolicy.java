package com.imi.entity.reportPolicy;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.entity.AbstractEntity;
import com.imi.entity.setting.MemberUser;

/**
 * 保险公司会员单位上报的保单主信息
 * @author Devon
 *
 */
@Entity
@Table(name = "biz_policy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_policy")
public class ReportPolicy extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	
	private String policyNo;//上报的保单号
	private String appntName;//保险人名称
	private String insuredName;//被保险名称
	private String riskName;//险种名称
	private String addRiskName;//附加险
	private String riskType;//险种类型
	private String policyUploadUnit;//保单上报的会员单位
	private String franchise;//责任免赔额
	private BigDecimal prem;//保费
	private BigDecimal amount;//保额
	private String mainTerm;//主条款
	private String addTerm;//附加条款
	private String currency;//币种
	private String policyStartTime;//保险起始时间
	private String policyEndTime;//保险结束时间
	private BigDecimal policyRate;//总保险费率（%）
	private String vesselsName;//船舶名称
	private String vesselsType;//船舶类型
	private String vesselsNo;//船舶登记号IMO
	private String produceTime;//建造年份
	private String totalTonnage;//总吨位
	private String vesselsLevel;//船级
	private String vesselsPort;//船籍港
	private String tradingLimit;//航行区域
	private BigDecimal addPrem;//附加险保费
	private String judicialControl;//司法管辖地
	private BigDecimal accidentLimit;//每次事故责任限额
	private BigDecimal totalAccidentLimit;//累计责任限额
	private String insuredMark;//保险标的
	private BigDecimal insuredMarkQuantity;//标的数量
	private String sailingDate;//开航日期/起运日期
	private String loadingName;//装载运输工具名称
	private String startPlace;//起运地
	private String endPlace;//目的地
	private String claimPlace;//赔付地点
	private Date createdTime;//数据创建时间
	private Date modifiedTime;//数据修改时间
	private String isOffshoreVessels;//是否是进外船舶险
	
	/**  可能补充字段 客户需求，暂时都定义成String,对于下面带下划线的重复字段
	 *   是因为客户说暂存成文本，所以只能这样，等再次确定后，请将其规定到上面已有的字段里 **/
	private String producer;//建造商
	private String policyValue;//保险价值
	private String vesselsAmount;//船壳险保险金额
	private String vesselsRate;//船壳险费率
	private String specialAgreement;//特别约定
	private String useLimit;//使用范围
	private String underwriteArea;//承保区域
	private String payType;//付费方法
	private String billNo;//提单号/运单号
	private String contractNo;//发票号\合同号
	private String goodsQuantity;//包装与数量
	private String goodsName;//保险货物名称
	private String invoiceAmount;//发票金额
	private String shippingType;//运输方式
	private String vesselsAge;//船龄
	private String vesselsUse;//船舶用途
	private String accidentLimit_05PI;//每次事故责任限额_05PI
	private String policyRate_08CT;//总保险费率（%）
	private String totalAccidentLimit_05OP;//累计责任限额05OP
	/**  可能补充字段 **/
	private BigDecimal franchise_05OP;//责任免赔额05OP
	
	private MemberUser user;// 联系人姓名、联系人电话、联系人邮箱、法律责任人\产品注册人
	
	@Column(name="franchise_05op")
	public BigDecimal getFranchise_05OP() {
		return franchise_05OP;
	}
	public void setFranchise_05OP(BigDecimal franchise_05op) {
		franchise_05OP = franchise_05op;
	}
	@Column(name="policy_no")
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	
	@Column(name="appnt_name")
	public String getAppntName() {
		return appntName;
	}
	public void setAppntName(String appntName) {
		this.appntName = appntName;
	}
	
	@Column(name="insured_name")
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	
	@Column(name="risk_name")
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	
	@Column(name="risk_type")
	public String getRiskType() {
		return riskType;
	}
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}
	
	@Column(name="policy_upload_unit")
	public String getPolicyUploadUnit() {
		return policyUploadUnit;
	}
	public void setPolicyUploadUnit(String policyUploadUnit) {
		this.policyUploadUnit = policyUploadUnit;
	}
	
	@Column(name="franchise")
	public String getFranchise() {
		return franchise;
	}
	public void setFranchise(String franchise) {
		this.franchise = franchise;
	}
	
	@Column(name="prem")
	public BigDecimal getPrem() {
		return prem;
	}
	public void setPrem(BigDecimal prem) {
		this.prem = prem;
	}
	
	@Column(name="amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name="main_term")
	public String getMainTerm() {
		return mainTerm;
	}
	public void setMainTerm(String mainTerm) {
		this.mainTerm = mainTerm;
	}
	
	@Column(name="add_term")
	public String getAddTerm() {
		return addTerm;
	}
	public void setAddTerm(String addTerm) {
		this.addTerm = addTerm;
	}
	
	@Column(name="currency")
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name="policy_start_time")
	public String getPolicyStartTime() {
		return policyStartTime;
	}
	public void setPolicyStartTime(String policyStartTime) {
		this.policyStartTime = policyStartTime;
	}
	
	@Column(name="policy_end_time")
	public String getPolicyEndTime() {
		return policyEndTime;
	}
	public void setPolicyEndTime(String policyEndTime) {
		this.policyEndTime = policyEndTime;
	}
	
	@Column(name="policy_rate")
	public BigDecimal getPolicyRate() {
		return policyRate;
	}
	public void setPolicyRate(BigDecimal policyRate) {
		this.policyRate = policyRate;
	}
	@Column(name="created_time")
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@Column(name="modified_time")
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	@Column(name="add_risk_name")
	public String getAddRiskName() {
		return addRiskName;
	}
	public void setAddRiskName(String addRiskName) {
		this.addRiskName = addRiskName;
	}
	
	@Column(name="vessels_name")
	public String getVesselsName() {
		return vesselsName;
	}
	public void setVesselsName(String vesselsName) {
		this.vesselsName = vesselsName;
	}
	
	@Column(name="vessels_type")
	public String getVesselsType() {
		return vesselsType;
	}
	public void setVesselsType(String vesselsType) {
		this.vesselsType = vesselsType;
	}
	
	@Column(name="vessels_no")
	public String getVesselsNo() {
		return vesselsNo;
	}
	public void setVesselsNo(String vesselsNo) {
		this.vesselsNo = vesselsNo;
	}
	@Column(name="produce_time")
	public String getProduceTime() {
		return produceTime;
	}
	public void setProduceTime(String produceTime) {
		this.produceTime = produceTime;
	}
	
	@Column(name="total_tonnage")
	public String getTotalTonnage() {
		return totalTonnage;
	}
	public void setTotalTonnage(String totalTonnage) {
		this.totalTonnage = totalTonnage;
	}
	
	@Column(name="vessels_level")
	public String getVesselsLevel() {
		return vesselsLevel;
	}
	public void setVesselsLevel(String vesselsLevel) {
		this.vesselsLevel = vesselsLevel;
	}
	
	@Column(name="vessels_port")
	public String getVesselsPort() {
		return vesselsPort;
	}
	public void setVesselsPort(String vesselsPort) {
		this.vesselsPort = vesselsPort;
	}
	
	@Column(name="trading_limit")
	public String getTradingLimit() {
		return tradingLimit;
	}
	public void setTradingLimit(String tradingLimit) {
		this.tradingLimit = tradingLimit;
	}
	
	@Column(name="add_prem")
	public BigDecimal getAddPrem() {
		return addPrem;
	}
	public void setAddPrem(BigDecimal addPrem) {
		this.addPrem = addPrem;
	}
	
	@Column(name="judicial_control")
	public String getJudicialControl() {
		return judicialControl;
	}
	public void setJudicialControl(String judicialControl) {
		this.judicialControl = judicialControl;
	}
	
	@Column(name="accident_limit")
	public BigDecimal getAccidentLimit() {
		return accidentLimit;
	}
	public void setAccidentLimit(BigDecimal accidentLimit) {
		this.accidentLimit = accidentLimit;
	}
	
	@Column(name="total_accident_limit")
	public BigDecimal getTotalAccidentLimit() {
		return totalAccidentLimit;
	}
	public void setTotalAccidentLimit(BigDecimal totalAccidentLimit) {
		this.totalAccidentLimit = totalAccidentLimit;
	}
	
	@Column(name="insured_mark")
	public String getInsuredMark() {
		return insuredMark;
	}
	public void setInsuredMark(String insuredMark) {
		this.insuredMark = insuredMark;
	}
	
	@Column(name="insured_mark_quantity")
	public BigDecimal getInsuredMarkQuantity() {
		return insuredMarkQuantity;
	}
	public void setInsuredMarkQuantity(BigDecimal insuredMarkQuantity) {
		this.insuredMarkQuantity = insuredMarkQuantity;
	}
	
	@Column(name="sailing_date")
	public String getSailingDate() {
		return sailingDate;
	}
	public void setSailingDate(String sailingDate) {
		this.sailingDate = sailingDate;
	}
	
	@Column(name="loading_name")
	public String getLoadingName() {
		return loadingName;
	}
	public void setLoadingName(String loadingName) {
		this.loadingName = loadingName;
	}

	@Column(name="start_place")
	public String getStartPlace() {
		return startPlace;
	}
	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}
	
	@Column(name="end_place")
	public String getEndPlace() {
		return endPlace;
	}
	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}
	
	@Column(name="claim_place")
	public String getClaimPlace() {
		return claimPlace;
	}
	public void setClaimPlace(String claimPlace) {
		this.claimPlace = claimPlace;
	}
	
	@Column(name="producer")
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	
	@Column(name="policy_value")
	public String getPolicyValue() {
		return policyValue;
	}
	public void setPolicyValue(String policyValue) {
		this.policyValue = policyValue;
	}
	
	@Column(name="vessels_amount")
	public String getVesselsAmount() {
		return vesselsAmount;
	}
	public void setVesselsAmount(String vesselsAmount) {
		this.vesselsAmount = vesselsAmount;
	}
	
	@Column(name="vessels_rate")
	public String getVesselsRate() {
		return vesselsRate;
	}
	public void setVesselsRate(String vesselsRate) {
		this.vesselsRate = vesselsRate;
	}
	
	@Column(name="special_agreement")
	public String getSpecialAgreement() {
		return specialAgreement;
	}
	public void setSpecialAgreement(String specialAgreement) {
		this.specialAgreement = specialAgreement;
	}
	
	@Column(name="use_limit")
	public String getUseLimit() {
		return useLimit;
	}
	public void setUseLimit(String useLimit) {
		this.useLimit = useLimit;
	}
	
	@Column(name="underwrite_area")
	public String getUnderwriteArea() {
		return underwriteArea;
	}
	public void setUnderwriteArea(String underwriteArea) {
		this.underwriteArea = underwriteArea;
	}
	
	@Column(name="pay_type")
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Column(name="bill_no")
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	@Column(name="contract_no")
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	@Column(name="goods_quantity")
	public String getGoodsQuantity() {
		return goodsQuantity;
	}
	public void setGoodsQuantity(String goodsQuantity) {
		this.goodsQuantity = goodsQuantity;
	}
	
	@Column(name="goods_name")
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@Column(name="invoice_amount")
	public String getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	@Column(name="shipping_type")
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	
	@Column(name="vessels_age")
	public String getVesselsAge() {
		return vesselsAge;
	}
	public void setVesselsAge(String vesselsAge) {
		this.vesselsAge = vesselsAge;
	}
	
	@Column(name="vessels_use")
	public String getVesselsUse() {
		return vesselsUse;
	}
	public void setVesselsUse(String vesselsUse) {
		this.vesselsUse = vesselsUse;
	}
	
	@Column(name="accident_limit_05pi")
	public String getAccidentLimit_05PI() {
		return accidentLimit_05PI;
	}
	public void setAccidentLimit_05PI(String accidentLimit_05PI) {
		this.accidentLimit_05PI = accidentLimit_05PI;
	}
	
	@Column(name="policy_rate_08ct")
	public String getPolicyRate_08CT() {
		return policyRate_08CT;
	}
	public void setPolicyRate_08CT(String policyRate_08CT) {
		this.policyRate_08CT = policyRate_08CT;
	}

	@Column(name="total_accident_limit_08ct")
	public String getTotalAccidentLimit_05OP() {
		return totalAccidentLimit_05OP;
	}
	public void setTotalAccidentLimit_05OP(String totalAccidentLimit_05OP) {
		this.totalAccidentLimit_05OP = totalAccidentLimit_05OP;
	}
	
	@Column(name="is_offshore_vessels")
	public String getIsOffshoreVessels() {
		return isOffshoreVessels;
	}
	public void setIsOffshoreVessels(String isOffshoreVessels) {
		this.isOffshoreVessels = isOffshoreVessels;
	}	
}
