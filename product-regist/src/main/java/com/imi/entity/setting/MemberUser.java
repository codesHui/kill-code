package com.imi.entity.setting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.BaseEntity;
import com.imi.entity.security.User;


/**
 * 会员用户
 * @author josh
 *
 */
@Entity
@Table(name = "biz_member_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class MemberUser  extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//公司名称、公司简称 、公司官网网址、组织机构代码、法律责任人、公司简介（不用）、专用邮箱、产品联系人1（姓名、职位、手机邮箱）、产品联系人2（姓名、职位、手机邮箱）、产品注册权（不用了）、注册人代码,注册时间、注册人
    
	private String  companyName,companyShortName,companyWebsit,organizationCode,legalPerson,introduction,specialMail,registrationRights,registeredCode,registeredDate,registeredPerson;
	private Integer  status, companyType,operType, swiftNum;//状态，公司类别、操作状态(正常1、暂停注册权2)、流水号（默认为一）;	
	private String contactPerson1Name,contactPerson1Phone,contactPerson1Email;
	private String contactPerson2Name,contactPerson2Phone,contactPerson2Email;
	private User user;

	@Column(name="companyName")
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name="companyWebsit")
	public String getCompanyWebsit() {
		return companyWebsit;
	}
	public void setCompanyWebsit(String companyWebsit) {
		this.companyWebsit = companyWebsit;
	}
	@Column(name="organizationCode")
	public String getOrganizationCode() {
		return organizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	@Column(name="legalPerson")
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	@Column(name="specialMail")
	public String getSpecialMail() {
		return specialMail;
	}
	public void setSpecialMail(String specialMail) {
		this.specialMail = specialMail;
	}
	@Column(name="registrationRights")
	public String getRegistrationRights() {
		return registrationRights;
	}
	public void setRegistrationRights(String registrationRights) {
		this.registrationRights = registrationRights;
	}
	@Column(name="registeredCode")
	public String getRegisteredCode() {
		return registeredCode;
	}
	public void setRegisteredCode(String registeredCode) {
		this.registeredCode = registeredCode;
	}
	@Column(name="contactPerson1Name")
	public String getContactPerson1Name() {
		return contactPerson1Name;
	}
	public void setContactPerson1Name(String contactPerson1Name) {
		this.contactPerson1Name = contactPerson1Name;
	}
	@Column(name="contactPerson1Phone")
	public String getContactPerson1Phone() {
		return contactPerson1Phone;
	}
	public void setContactPerson1Phone(String contactPerson1Phone) {
		this.contactPerson1Phone = contactPerson1Phone;
	}
	@Column(name="contactPerson1Email")
	public String getContactPerson1Email() {
		return contactPerson1Email;
	}
	public void setContactPerson1Email(String contactPerson1Email) {
		this.contactPerson1Email = contactPerson1Email;
	}
	@Column(name="contactPerson2Name")
	public String getContactPerson2Name() {
		return contactPerson2Name;
	}
	public void setContactPerson2Name(String contactPerson2Name) {
		this.contactPerson2Name = contactPerson2Name;
	}
	@Column(name="contactPerson2Phone")
	public String getContactPerson2Phone() {
		return contactPerson2Phone;
	}
	public void setContactPerson2Phone(String contactPerson2Phone) {
		this.contactPerson2Phone = contactPerson2Phone;
	}
	@Column(name="contactPerson2Email")
	public String getContactPerson2Email() {
		return contactPerson2Email;
	}
	public void setContactPerson2Email(String contactPerson2Email) {
		this.contactPerson2Email = contactPerson2Email;
	}
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name="introduction")
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Column(name="operType")
	public Integer getOperType() {
		return operType;
	}
	public void setOperType(Integer operType) {
		this.operType = operType;
	}
	@Column(name="companyType")
	public Integer getCompanyType() {
		return companyType;
	}
	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	@Column(name="status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="swiftNum")
	public Integer getSwiftNum() {
		return swiftNum;
	}
	public void setSwiftNum(Integer swiftNum) {
		this.swiftNum = swiftNum;
	}
	@Column(name="companyShortName")
	public String getCompanyShortName() {
		return companyShortName;
	}
	@Column(name="registeredPerson")
	public String getRegisteredPerson() {
		return registeredPerson;
	}
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}
	public void setRegisteredPerson(String registeredPerson) {
		this.registeredPerson = registeredPerson;
	}
	@Column(name="registeredDate")
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	@Override
	public String toString() {
		return "MemberUser [companyName=" + companyName + ", companyShortName=" + companyShortName + ", companyWebsit="
				+ companyWebsit + ", organizationCode=" + organizationCode + ", legalPerson=" + legalPerson
				+ ", introduction=" + introduction + ", specialMail=" + specialMail + ", registrationRights="
				+ registrationRights + ", registeredCode=" + registeredCode + ", registeredDate=" + registeredDate
				+ ", registeredPerson=" + registeredPerson + ", status=" + status + ", companyType=" + companyType
				+ ", operType=" + operType + ", swiftNum=" + swiftNum + ", contactPerson1Name=" + contactPerson1Name
				+ ", contactPerson1Phone=" + contactPerson1Phone + ", contactPerson1Email=" + contactPerson1Email
				+ ", contactPerson2Name=" + contactPerson2Name + ", contactPerson2Phone=" + contactPerson2Phone
				+ ", contactPerson2Email=" + contactPerson2Email + ", user=" + user + "]";
	}

}
