package com.imi.model.product;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.MemberUser;
import com.softvan.model.Paging;
/**
 * 产品信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ProductInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private int orderNo, status, productType, protectionType;
	// 产品注册号、产品类别、产品名称、产品语言、中文名称、险类名称、险种名称、条款保护期、
	private String productNO, productName, productLanguage, chineseName,offreason,riskType,riskName,registPerson;
	private MemberUser user;// 联系人姓名、联系人电话、联系人邮箱、法律责任人\产品注册人
	private int currentRowNo;
	private Set<Attachment>	attachment;
	private String title, keywords, description;// 备注
	private  String contactPersonName,contactPersonPhone,contactPersonEmail;//联系人、联系电话、联系邮件
	private String accreditationCompany;//认可公司
	private Date registTime;//注册日期

	
	private String registTime_left;
	private String registTime_right;
	private String registeredCode;
	
	// 同Product类中offreasonStatus字段相同意思
	private String offreasonStatus;
	
	private String proRegReason;//外文产品注册原因
	
	//主险 附加险数量
	private Integer mainSafe,addSafe;

	public Integer getMainSafe() {
		return mainSafe;
	}

	public void setMainSafe(Integer mainSafe) {
		this.mainSafe = mainSafe;
	}

	public Integer getAddSafe() {
		return addSafe;
	}

	public void setAddSafe(Integer addSafe) {
		this.addSafe = addSafe;
	}

	public String getOffreasonStatus() {
		return offreasonStatus;
	}
	public void setOffreasonStatus(String offreasonStatus) {
		this.offreasonStatus = offreasonStatus;
	}
	
	public String getRegisteredCode() {
		return registeredCode;
	}
	public void setRegisteredCode(String registeredCode) {
		this.registeredCode = registeredCode;
	}

	private List<Product> list;
	private Product product;
	private Verifys verifys;
	private List<ProductInfo> listInfo;
	private int total;
	private List<Verifys> vList;
	private List<Complaints> cList;
	
	private Long currentUserId;
	
	
	
	public List<Complaints> getcList() {
		return cList;
	}
	public void setcList(List<Complaints> cList) {
		this.cList = cList;
	}
	public List<Verifys> getvList() {
		return vList;
	}
	public void setvList(List<Verifys> vList) {
		this.vList = vList;
	}
	public List<ProductInfo> getListInfo() {
		return listInfo;
	}
	public void setListInfo(List<ProductInfo> listInfo) {
		this.listInfo = listInfo;
	}
	public Verifys getVerifys() {
		return verifys;
	}
	public void setVerifys(Verifys verifys) {
		this.verifys = verifys;
	}
	public Long getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Product> getList() {
		return list;
	}
	public void setList(List<Product> list) {
		this.list = list;
	}
	/**
	 * 构造函数。
	 */
	public ProductInfo(){
	}
	/**
	 * orderNoを取得します。
	 * @return orderNo
	 */
	public int getOrderNo() {
	    return orderNo;
	}
	/**
	 * orderNoを設定します。
	 * @param orderNo orderNo
	 */
	public void setOrderNo(int orderNo) {
	    this.orderNo = orderNo;
	}
	/**
	 * statusを取得します。
	 * @return status
	 */
	public int getStatus() {
	    return status;
	}
	/**
	 * statusを設定します。
	 * @param status status
	 */
	public void setStatus(int status) {
	    this.status = status;
	}
	/**
	 * productTypeを取得します。
	 * @return productType
	 */
	public int getProductType() {
	    return productType;
	}
	/**
	 * productTypeを設定します。
	 * @param productType productType
	 */
	public void setProductType(int productType) {
	    this.productType = productType;
	}
	/**
	 * protectionTypeを取得します。
	 * @return protectionType
	 */
	public int getProtectionType() {
	    return protectionType;
	}
	/**
	 * protectionTypeを設定します。
	 * @param protectionType protectionType
	 */
	public void setProtectionType(int protectionType) {
	    this.protectionType = protectionType;
	}
	/**
	 * productNOを取得します。
	 * @return productNO
	 */
	public String getProductNO() {
	    return productNO;
	}
	/**
	 * productNOを設定します。
	 * @param productNO productNO
	 */
	public void setProductNO(String productNO) {
	    this.productNO = productNO;
	}
	/**
	 * productNameを取得します。
	 * @return productName
	 */
	public String getProductName() {
	    return productName;
	}
	/**
	 * productNameを設定します。
	 * @param productName productName
	 */
	public void setProductName(String productName) {
	    this.productName = productName;
	}
	/**
	 * productLanguageを取得します。
	 * @return productLanguage
	 */
	public String getProductLanguage() {
	    return productLanguage;
	}
	/**
	 * productLanguageを設定します。
	 * @param productLanguage productLanguage
	 */
	public void setProductLanguage(String productLanguage) {
	    this.productLanguage = productLanguage;
	}
	/**
	 * chineseNameを取得します。
	 * @return chineseName
	 */
	public String getChineseName() {
	    return chineseName;
	}
	/**
	 * chineseNameを設定します。
	 * @param chineseName chineseName
	 */
	public void setChineseName(String chineseName) {
	    this.chineseName = chineseName;
	}
	/**
	 * offreasonを取得します。
	 * @return offreason
	 */
	public String getOffreason() {
	    return offreason;
	}
	/**
	 * offreasonを設定します。
	 * @param offreason offreason
	 */
	public void setOffreason(String offreason) {
	    this.offreason = offreason;
	}
	/**
	 * riskTypeを取得します。
	 * @return riskType
	 */
	public String getRiskType() {
	    return riskType;
	}
	/**
	 * riskTypeを設定します。
	 * @param riskType riskType
	 */
	public void setRiskType(String riskType) {
	    this.riskType = riskType;
	}
	/**
	 * riskNameを取得します。
	 * @return riskName
	 */
	public String getRiskName() {
	    return riskName;
	}
	/**
	 * riskNameを設定します。
	 * @param riskName riskName
	 */
	public void setRiskName(String riskName) {
	    this.riskName = riskName;
	}
	/**
	 * userを設定します。
	 * @param user user
	 */
	public void setUser(MemberUser user) {
	    this.user = user;
	}
	/**
	 * registPersonを取得します。
	 * @return registPerson
	 */
	public String getRegistPerson() {
	    return registPerson;
	}
	/**
	 * registPersonを設定します。
	 * @param registPerson registPerson
	 */
	public void setRegistPerson(String registPerson) {
	    this.registPerson = registPerson;
	}
	/**
	 * userを取得します。
	 * @return user
	 */
	public MemberUser getUser() {
	    return user;
	}
	/**
	 * attachmentを取得します。
	 * @return attachment
	 */
	public Set<Attachment> getAttachment() {
	    return attachment;
	}
	/**
	 * attachmentを設定します。
	 * @param attachment attachment
	 */
	public void setAttachment(Set<Attachment> attachment) {
	    this.attachment = attachment;
	}
	/**
	 * titleを取得します。
	 * @return title
	 */
	public String getTitle() {
	    return title;
	}
	/**
	 * titleを設定します。
	 * @param title title
	 */
	public void setTitle(String title) {
	    this.title = title;
	}
	/**
	 * keywordsを取得します。
	 * @return keywords
	 */
	public String getKeywords() {
	    return keywords;
	}
	/**
	 * keywordsを設定します。
	 * @param keywords keywords
	 */
	public void setKeywords(String keywords) {
	    this.keywords = keywords;
	}
	/**
	 * descriptionを取得します。
	 * @return description
	 */
	public String getDescription() {
	    return description;
	}
	/**
	 * descriptionを設定します。
	 * @param description description
	 */
	public void setDescription(String description) {
	    this.description = description;
	}
	public int getCurrentRowNo(){
		return  currentRowNo;
	}
	public void setCurrentRowNo(int currentRowNo) {
		this.currentRowNo = currentRowNo;
	}
	/**
	 * contactPersonNameを取得します。
	 * @return contactPersonName
	 */
	public String getContactPersonName() {
	    return contactPersonName;
	}
	/**
	 * contactPersonNameを設定します。
	 * @param contactPersonName contactPersonName
	 */
	public void setContactPersonName(String contactPersonName) {
	    this.contactPersonName = contactPersonName;
	}
	/**
	 * contactPersonPhoneを取得します。
	 * @return contactPersonPhone
	 */
	public String getContactPersonPhone() {
	    return contactPersonPhone;
	}
	/**
	 * contactPersonPhoneを設定します。
	 * @param contactPersonPhone contactPersonPhone
	 */
	public void setContactPersonPhone(String contactPersonPhone) {
	    this.contactPersonPhone = contactPersonPhone;
	}
	/**
	 * contactPersonEmailを取得します。
	 * @return contactPersonEmail
	 */
	public String getContactPersonEmail() {
	    return contactPersonEmail;
	}
	/**
	 * contactPersonEmailを設定します。
	 * @param contactPersonEmail contactPersonEmail
	 */
	public void setContactPersonEmail(String contactPersonEmail) {
	    this.contactPersonEmail = contactPersonEmail;
	}
	/**
	 * accreditationCompanyを取得します。
	 * @return accreditationCompany
	 */
	public String getAccreditationCompany() {
	    return accreditationCompany;
	}
	/**
	 * accreditationCompanyを設定します。
	 * @param accreditationCompany accreditationCompany
	 */
	public void setAccreditationCompany(String accreditationCompany) {
	    this.accreditationCompany = accreditationCompany;
	}
	/**
	 * registTimeを取得します。
	 * @return registTime
	 */
	public Date getRegistTime() {
	    return registTime;
	}
	/**
	 * registTimeを設定します。
	 * @param registTime registTime
	 */
	public void setRegistTime(Date registTime) {
	    this.registTime = registTime;
	}
	/**
	 * totalを取得します。
	 * @return total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * totalを設定します。
	 * @param total total
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	public String getRegistTime_left() {
		return registTime_left;
	}
	public void setRegistTime_left(String registTime_left) {
		this.registTime_left = registTime_left;
	}
	public String getRegistTime_right() {
		return registTime_right;
	}
	public void setRegistTime_right(String registTime_right) {
		this.registTime_right = registTime_right;
	}
	public String getProRegReason() {
		return proRegReason;
	}
	public void setProRegReason(String proRegReason) {
		this.proRegReason = proRegReason;
	}
	

	
	
	
}