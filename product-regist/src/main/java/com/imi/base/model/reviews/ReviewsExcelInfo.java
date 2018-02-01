package com.imi.base.model.reviews;

import com.imi.base.annotate.ReportColumn;
import com.imi.entity.products.Reviews;


import java.io.Serializable;
import java.util.Date;

/**
 * 导出Reviews信息Excel表的中转类
 */
public class ReviewsExcelInfo implements Serializable{

    private Reviews reviews;//转换的数据

    private int productIndex;//序号

    private String productNO;//产品注册号

    private String productName;//产品名称

    private String registPerson;//产品注册人

    private String productLanguage;//语言

    private String chineseName;//中文名称

    private int productType;//类别

    private String riskType;//险类

    private String riskName;//险种名称

    private Date registTime;//注册时间

    private String flowOperate;//复查意见

    private String flowReason;//复查描述

    private String flowMan;//复查人 //查出的信息导致BUG

    private Date flowDate;//复查时间

    private String flowOperate3;//抽查意见

    private String flowReason3;//抽查描述

    private String flowMan3;//抽查人

    private Date flowDate3;//抽查时间

    /*整改系列信息*/
    private String changeTellDate;//整改通知时间

    private String changeResult;//整改结果

    private Date changeEndDate;//整改完成时间

    public ReviewsExcelInfo() {
    }

    public ReviewsExcelInfo(Reviews reviews) {
        //产品信息
        this.productNO = reviews.getProduct().getProductNO();
        this.productName = reviews.getProduct().getProductName();
        this.registPerson = reviews.getProduct().getRegistPerson();
        this.productLanguage = reviews.getProduct().getProductLanguage();
        this.chineseName = reviews.getProduct().getChineseName();
        this.productType = reviews.getProduct().getProductType();
        this.riskType = reviews.getProduct().getRiskType();
        this.riskName = reviews.getProduct().getRiskName() != null ? reviews.getProduct().getRiskName() : "";//产品分类名称
        this.registTime = reviews.getProduct().getRegistTime();

    }

    public int getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(int productIndex) {
        this.productIndex = productIndex;
    }

    public String getProductNO() {
        return productNO;
    }

    public void setProductNO(String productNO) {
        this.productNO = productNO;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRegistPerson() {
        return registPerson;
    }

    public void setRegistPerson(String registPerson) {
        this.registPerson = registPerson;
    }

    public String getProductLanguage() {
        return productLanguage;
    }

    public void setProductLanguage(String productLanguage) {
        this.productLanguage = productLanguage;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
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

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public String getFlowOperate() {
        return flowOperate;
    }

    //复查人意见 判断逻辑
    public void setFlowOperate(String flowOperate1,String flowOperate2) {
        if (flowOperate1 != null){
            this.flowOperate = flowOperate1;
        }else if (flowOperate2 != null){
            this.flowOperate = flowOperate2;
        }

    }

    public String getFlowReason() {
        return flowReason;
    }

    //复查描述判断
    public void setFlowReason(String flowReason1,String flowReason2) {
        if (flowReason1 != null){
            this.flowReason = flowReason1;
        }else if(flowReason2 != null){
            this.flowReason = flowReason2;
        }
    }

    public String getFlowMan() {
        //复查人角色名判断
        try {
            if (reviews.getFlowMan1().getNickName() != null){
                return reviews.getFlowMan1().getNickName();
            }
        }catch (Exception e){
            try{
                if(reviews.getFlowMan2().getNickName() != null){
                    return reviews.getFlowMan2().getNickName();
                }
            }catch (Exception e1){
                if (reviews.getFlowMan1() == null){
                    return flowMan;
                }else if(reviews.getFlowMan2() == null){
                    return flowMan;
                }
            }

        }
        return null;
    }

    //复查人判断
    public void setFlowMan(String flowMan1,String flowMan2) {
       if (flowMan1 != null){
           this.flowMan = flowMan1;
       }else if (flowMan2 != null){
           this.flowMan = flowMan2;
       }
    }


    public Date getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(Date flowDate1,Date flowDate2) {
        if (flowDate1 != null){
            this.flowDate = flowDate1;
        }else if (flowDate2 != null){
            this.flowDate = flowDate2;
        }
    }

    public String getFlowOperate3() {
        return flowOperate3;
    }

    public void setFlowOperate3(String flowOperate3) {
        this.flowOperate3 = flowOperate3;
    }

    public String getFlowReason3() {
        return flowReason3;
    }

    public void setFlowReason3(String flowReason3) {
        this.flowReason3 = flowReason3;
    }

    public String getFlowMan3() {
        //反射得到的数据是从属性的get方法中得到
        try {
            if (reviews.getFlowMan3().getNickName() != null){
                return reviews.getFlowMan3().getNickName();
            }
        }catch (Exception e){
                return flowMan3;
        }
        return " ";
    }

    public void setFlowMan3(String flowMan3) {
        this.flowMan3 = flowMan3;
    }

    public Date getFlowDate3() {
        return flowDate3;
    }

    public void setFlowDate3(Date flowDate3) {
        this.flowDate3 = flowDate3;
    }

    public String getChangeTellDate() {
        return changeTellDate;
    }

    public void setChangeTellDate(String changeTellDate) {
        this.changeTellDate = changeTellDate;
    }

    public String getChangeResult() {
        return changeResult;
    }

    public void setChangeResult(String changeResult) {
        this.changeResult = changeResult;
    }

    public Date getChangeEndDate() {
        return changeEndDate;
    }

    public void setChangeEndDate(Date changeEndDate) {
        this.changeEndDate = changeEndDate;
    }
}
