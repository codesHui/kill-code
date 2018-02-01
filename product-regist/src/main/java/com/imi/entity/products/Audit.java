package com.imi.entity.products;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import jdk.nashorn.internal.objects.annotations.Constructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.BaseEntity;
import com.imi.base.entity.Orderable;
import com.imi.entity.setting.MemberUser;

/**
 * 产品。
 *
 */
@Entity
@Table(name = "biz_test")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "biz_test")
public class Audit extends BaseEntity implements Orderable {
    private static final long serialVersionUID = 1L;

    // 条款保护期 1/保护器期   2/未保护
    private int protectionType;
    private int orderNo,productType,productFlag;
    // 产品注册号、产品类别、产品名称、产品语言、中文名称、条款保护期、险类名称、险种名称,问题产品标志（1:问题产品，0:正常）
    private String productNO, productName, productLanguage, chineseName,offreason,riskType,riskName,registPerson;
    private MemberUser user;// 联系人姓名、联系人电话、联系人邮箱、法律责任人\产品注册人

    private String title, keywords, description;// 备注

    private  String contactPersonName,contactPersonPhone,contactPersonEmail;//联系人、联系电话、联系邮件
    private String accreditationCompany;//认可公司

    private Date registTime;//注册日期

    // 产品注销状态  1/强制注销   其它/未注销或普通注销
    private String logoutType;
    // 4/产品注销
    private int status;



    private int productIndex;

    public int getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(int productIndex) {
        this.productIndex = productIndex;
    }
    /**
     * 产品注销类别
     *  1/条款停止使用
     2/修改保险责任
     3/修改除外责任
     4/注册产品条件有误
     5/注册条款中有错别字
     6/注册时操作失误
     7/其他
     */
    private String offreasonStatus;

    private String proRegReason;//外文产品注册原因

    //主险 附加险数量
    @Column(name="mainSafe")
    private Integer mainSafe;
    @Column(name="addSafe")
    private Integer addSafe;

    //审查状态 0未审核 1通过 2未通过
    @Column(name="review")
    private Integer review;

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

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

    @Column(name="offreasonStatus")
    public String getOffreasonStatus() {
        return offreasonStatus;
    }


    public void setOffreasonStatus(String offreasonStatus) {
        this.offreasonStatus = offreasonStatus;
    }


    /**
     * contactPersonNameを取得します。
     * @return contactPersonName
     */
    @Column(name="contactPersonName")
    public String getContactPersonName() {
        return contactPersonName;
    }


    /**
     * contactPersonPhoneを取得します。
     * @return contactPersonPhone
     */
    @Column(name="contactPersonPhone")
    public String getContactPersonPhone() {
        return contactPersonPhone;
    }


    /**
     * contactPersonEmailを取得します。
     * @return contactPersonEmail
     */
    @Column(name="contactPersonEmail")
    public String getContactPersonEmail() {
        return contactPersonEmail;
    }


    /**
     * orderNoを取得します。
     * @return orderNo
     */
    @Column(name="orderNo")
    public int getOrderNo() {
        return orderNo;
    }


    /**
     * statusを取得します。
     * @return status
     */
    @Column(name="status")
    public int getStatus() {
        return status;
    }


    @Column(name="protectionType")
    public Integer getProtectionType() {
        return protectionType;
    }



    /**
     * productTypeを取得します。
     * @return productType
     */
    @Column(name="productType")
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
     * productNOを取得します。
     * @return productNO
     */
    @Column(name="productNO")
    public String getProductNO() {
        return productNO;
    }


    /**
     * productNameを取得します。
     * @return productName
     */
    @Column(name="productName")
    public String getProductName() {
        return productName;
    }


    /**
     * productLanguageを取得します。
     * @return productLanguage
     */
    @Column(name="productLanguage")
    public String getProductLanguage() {
        return productLanguage;
    }


    /**
     * registPersonを取得します。
     * @return registPerson
     */
    @Column(name="registPerson")
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
     * keywordsを取得します。
     * @return keywords
     */
    @Column(name="keywords")
    public String getKeywords() {
        return keywords;
    }


    @Column(name="description")
    public String getDescription() {
        return description;
    }
    /**
     * chineseNameを取得します。
     * @return chineseName
     */
    @Column(name="chineseName")
    public String getChineseName() {
        return chineseName;
    }


    /**
     * accreditationCompanyを取得します。
     * @return accreditationCompany
     */
    @Column(name="accreditationCompany")
    public String getAccreditationCompany() {
        return accreditationCompany;
    }


    public void setProtectionType(Integer protectionType) {
        this.protectionType = protectionType;
    }




    public void setProductNO(String productNO) {
        this.productNO = productNO;
    }




    public void setProductName(String productName) {
        this.productName = productName;
    }




    public void setProductLanguage(String productLanguage) {
        this.productLanguage = productLanguage;
    }




    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }




    /**
     * userを取得します。
     * @return user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registeredCode",referencedColumnName="registeredCode", nullable =true)
    public MemberUser getUser() {
        return user;
    }


    /**
     * userを設定します。
     * @param user user
     */
    public void setUser(MemberUser user) {
        this.user = user;
    }


    public String getTitle() {
        return title;
    }




    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }




    public void setStatus(int status) {
        this.status = status;
    }








    public void setProtectionType(int protectionType) {
        this.protectionType = protectionType;
    }








    public void setTitle(String title) {
        this.title = title;
    }




    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }




    public void setDescription(String description) {
        this.description = description;
    }




    @Override
    public int compareTo(Orderable o) {
        return (int) (getOrderNo() - o.getOrderNo());
    }








    /**
     * offreasonを取得します。
     * @return offreason
     */
    @Column(name="offreason")
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
    @Column(name="riskType")
    public String getRiskType() {
        return riskType;
    }

    @Column(name="productFlag")
    public int getProductFlag() {
        return productFlag;
    }

    /**
     * riskTypeを設定します。
     * @param riskType riskType
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }


    @Column(name="riskName")
    public String getRiskName() {
        return riskName;
    }




    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }



    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }



    public void setContactPersonPhone(String contactPersonPhone) {
        this.contactPersonPhone = contactPersonPhone;
    }



    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }



    public void setAccreditationCompany(String accreditationCompany) {
        this.accreditationCompany = accreditationCompany;
    }





    /**
     * registTimeを取得します。
     * @return registTime
     */
    @Column(name="registTime")
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





    public void setProductFlag(int productFlag) {
        this.productFlag = productFlag;
    }

    @Column(name="logoutType")
    public String getLogoutType() {
        return logoutType;
    }


    public void setLogoutType(String logoutType) {
        this.logoutType = logoutType;
    }

    @Column(name="proRegReason")
    public String getProRegReason() {
        return proRegReason;
    }

    public void setProRegReason(String proRegReason) {
        this.proRegReason = proRegReason;
    }





















/*	@Override
	public int compareTo(Product o) {
		int index = 0;
		if (this == o)
			return index;
		index = this.getOrderNo() - o.getOrderNo();
		if (index == 0) {
			index = this.getProductNO().compareToIgnoreCase(o.getProductNO());
			if (index == 0) {
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
*/

}