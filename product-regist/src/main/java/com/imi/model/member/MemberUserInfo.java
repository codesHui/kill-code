package com.imi.model.member;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.imi.entity.security.User;
import com.imi.entity.setting.MemberUser;
import com.softvan.model.Paging;
/**
 * 产品信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberUserInfo extends Paging {
	private static final long serialVersionUID = 1L;

	private Long id;
	//公司名称、公司简称 、公司官网网址、组织机构代码、法律责任人、公司简介（不用）、专用邮箱、产品联系人1（姓名、职位、手机邮箱）、产品联系人2（姓名、职位、手机邮箱）、产品注册权（不用了）、注册人代码,注册人
    
	private String  companyName,companyShortName,companyWebsit,organizationCode,legalPerson,introduction,specialMail,registrationRights,registeredCode,registeredPerson,registeredDate;
	private Integer  status, companyType,operType,swiftNum;	
	private String contactPerson1Name,contactPerson1Phone,contactPerson1Email;
	private String contactPerson2Name,contactPerson2Phone,contactPerson2Email;
	private User user;
	private int currentRowNo;
	private Long currentUserId;
	
	
	private String account;
	private String nickName;
	private String roleCode;
	private Integer userStatus;
	
	private List<MemberUser> memberList;
	
	private int total;
	
	
	public List<MemberUser> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<MemberUser> memberList) {
		this.memberList = memberList;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	/**
	 * idを取得します。
	 * @return id
	 */
	public Long getId() {
	    return id;
	}
	/**
	 * idを設定します。
	 * @param id id
	 */
	public void setId(Long id) {
	    this.id = id;
	}
	/**
	 * companyNameを取得します。
	 * @return companyName
	 */
	public String getCompanyName() {
	    return companyName;
	}
	/**
	 * companyNameを設定します。
	 * @param companyName companyName
	 */
	public void setCompanyName(String companyName) {
	    this.companyName = companyName;
	}
	/**
	 * companyShortNameを取得します。
	 * @return companyShortName
	 */
	public String getCompanyShortName() {
	    return companyShortName;
	}
	/**
	 * companyShortNameを設定します。
	 * @param companyShortName companyShortName
	 */
	public void setCompanyShortName(String companyShortName) {
	    this.companyShortName = companyShortName;
	}
	/**
	 * companyWebsitを取得します。
	 * @return companyWebsit
	 */
	public String getCompanyWebsit() {
	    return companyWebsit;
	}
	/**
	 * companyWebsitを設定します。
	 * @param companyWebsit companyWebsit
	 */
	public void setCompanyWebsit(String companyWebsit) {
	    this.companyWebsit = companyWebsit;
	}
	/**
	 * organizationCodeを取得します。
	 * @return organizationCode
	 */
	public String getOrganizationCode() {
	    return organizationCode;
	}
	/**
	 * organizationCodeを設定します。
	 * @param organizationCode organizationCode
	 */
	public void setOrganizationCode(String organizationCode) {
	    this.organizationCode = organizationCode;
	}
	/**
	 * legalPersonを取得します。
	 * @return legalPerson
	 */
	public String getLegalPerson() {
	    return legalPerson;
	}
	/**
	 * legalPersonを設定します。
	 * @param legalPerson legalPerson
	 */
	public void setLegalPerson(String legalPerson) {
	    this.legalPerson = legalPerson;
	}
	/**
	 * introductionを取得します。
	 * @return introduction
	 */
	public String getIntroduction() {
	    return introduction;
	}
	/**
	 * introductionを設定します。
	 * @param introduction introduction
	 */
	public void setIntroduction(String introduction) {
	    this.introduction = introduction;
	}
	/**
	 * specialMailを取得します。
	 * @return specialMail
	 */
	public String getSpecialMail() {
	    return specialMail;
	}
	/**
	 * specialMailを設定します。
	 * @param specialMail specialMail
	 */
	public void setSpecialMail(String specialMail) {
	    this.specialMail = specialMail;
	}
	/**
	 * registrationRightsを取得します。
	 * @return registrationRights
	 */
	public String getRegistrationRights() {
	    return registrationRights;
	}
	/**
	 * registrationRightsを設定します。
	 * @param registrationRights registrationRights
	 */
	public void setRegistrationRights(String registrationRights) {
	    this.registrationRights = registrationRights;
	}
	/**
	 * registeredCodeを取得します。
	 * @return registeredCode
	 */
	public String getRegisteredCode() {
	    return registeredCode;
	}
	/**
	 * registeredCodeを設定します。
	 * @param registeredCode registeredCode
	 */
	public void setRegisteredCode(String registeredCode) {
	    this.registeredCode = registeredCode;
	}
	/**
	 * registeredPersonを取得します。
	 * @return registeredPerson
	 */
	public String getRegisteredPerson() {
	    return registeredPerson;
	}
	/**
	 * registeredPersonを設定します。
	 * @param registeredPerson registeredPerson
	 */
	public void setRegisteredPerson(String registeredPerson) {
	    this.registeredPerson = registeredPerson;
	}
	/**
	 * registeredDateを取得します。
	 * @return registeredDate
	 */
	public String getRegisteredDate() {
	    return registeredDate;
	}
	/**
	 * registeredDateを設定します。
	 * @param registeredDate registeredDate
	 */
	public void setRegisteredDate(String registeredDate) {
	    this.registeredDate = registeredDate;
	}
	/**
	 * statusを取得します。
	 * @return status
	 */
	public Integer getStatus() {
	    return status;
	}
	/**
	 * statusを設定します。
	 * @param status status
	 */
	public void setStatus(Integer status) {
	    this.status = status;
	}
	/**
	 * companyTypeを取得します。
	 * @return companyType
	 */
	public Integer getCompanyType() {
	    return companyType;
	}
	/**
	 * companyTypeを設定します。
	 * @param companyType companyType
	 */
	public void setCompanyType(Integer companyType) {
	    this.companyType = companyType;
	}
	/**
	 * operTypeを取得します。
	 * @return operType
	 */
	public Integer getOperType() {
	    return operType;
	}
	/**
	 * operTypeを設定します。
	 * @param operType operType
	 */
	public void setOperType(Integer operType) {
	    this.operType = operType;
	}
	/**
	 * swiftNumを取得します。
	 * @return swiftNum
	 */
	public Integer getSwiftNum() {
	    return swiftNum;
	}
	/**
	 * swiftNumを設定します。
	 * @param swiftNum swiftNum
	 */
	public void setSwiftNum(Integer swiftNum) {
	    this.swiftNum = swiftNum;
	}
	/**
	 * contactPerson1Nameを取得します。
	 * @return contactPerson1Name
	 */
	public String getContactPerson1Name() {
	    return contactPerson1Name;
	}
	/**
	 * contactPerson1Nameを設定します。
	 * @param contactPerson1Name contactPerson1Name
	 */
	public void setContactPerson1Name(String contactPerson1Name) {
	    this.contactPerson1Name = contactPerson1Name;
	}
	/**
	 * contactPerson1Phoneを取得します。
	 * @return contactPerson1Phone
	 */
	public String getContactPerson1Phone() {
	    return contactPerson1Phone;
	}
	/**
	 * contactPerson1Phoneを設定します。
	 * @param contactPerson1Phone contactPerson1Phone
	 */
	public void setContactPerson1Phone(String contactPerson1Phone) {
	    this.contactPerson1Phone = contactPerson1Phone;
	}
	/**
	 * contactPerson1Emailを取得します。
	 * @return contactPerson1Email
	 */
	public String getContactPerson1Email() {
	    return contactPerson1Email;
	}
	/**
	 * contactPerson1Emailを設定します。
	 * @param contactPerson1Email contactPerson1Email
	 */
	public void setContactPerson1Email(String contactPerson1Email) {
	    this.contactPerson1Email = contactPerson1Email;
	}
	/**
	 * contactPerson2Nameを取得します。
	 * @return contactPerson2Name
	 */
	public String getContactPerson2Name() {
	    return contactPerson2Name;
	}
	/**
	 * contactPerson2Nameを設定します。
	 * @param contactPerson2Name contactPerson2Name
	 */
	public void setContactPerson2Name(String contactPerson2Name) {
	    this.contactPerson2Name = contactPerson2Name;
	}
	/**
	 * contactPerson2Phoneを取得します。
	 * @return contactPerson2Phone
	 */
	public String getContactPerson2Phone() {
	    return contactPerson2Phone;
	}
	/**
	 * contactPerson2Phoneを設定します。
	 * @param contactPerson2Phone contactPerson2Phone
	 */
	public void setContactPerson2Phone(String contactPerson2Phone) {
	    this.contactPerson2Phone = contactPerson2Phone;
	}
	/**
	 * contactPerson2Emailを取得します。
	 * @return contactPerson2Email
	 */
	public String getContactPerson2Email() {
	    return contactPerson2Email;
	}
	/**
	 * contactPerson2Emailを設定します。
	 * @param contactPerson2Email contactPerson2Email
	 */
	public void setContactPerson2Email(String contactPerson2Email) {
	    this.contactPerson2Email = contactPerson2Email;
	}
	/**
	 * userを取得します。
	 * @return user
	 */
	public User getUser() {
	    return user;
	}
	/**
	 * userを設定します。
	 * @param user user
	 */
	public void setUser(User user) {
	    this.user = user;
	}
	/**
	 * currentRowNoを取得します。
	 * @return currentRowNo
	 */
	public int getCurrentRowNo() {
	    return currentRowNo;
	}
	/**
	 * currentRowNoを設定します。
	 * @param currentRowNo currentRowNo
	 */
	public void setCurrentRowNo(int currentRowNo) {
	    this.currentRowNo = currentRowNo;
	}
	/**
	 * currentUserIdを取得します。
	 * @return currentUserId
	 */
	public Long getCurrentUserId() {
	    return currentUserId;
	}
	/**
	 * currentUserIdを設定します。
	 * @param currentUserId currentUserId
	 */
	public void setCurrentUserId(Long currentUserId) {
	    this.currentUserId = currentUserId;
	}
	
}