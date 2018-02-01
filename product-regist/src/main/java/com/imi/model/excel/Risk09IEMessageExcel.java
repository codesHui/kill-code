package com.imi.model.excel;

import java.math.BigDecimal;

import com.imi.base.annotate.ReportColumn;

/**
 * 进出口货运险
 * @author Devon
 *
 */
public class Risk09IEMessageExcel {
	@ReportColumn(columnName="保单号",columnOrder=1)
	private String policyNo;//上报的保单号
	@ReportColumn(columnName="保险人名称",columnOrder=2)
	private String appntName;//保险人名称
	@ReportColumn(columnName="被保险名称",columnOrder=3)
	private String insuredName;//被保险名称
	@ReportColumn(columnName="币种",columnOrder=4)
	private String currency;//币种
	@ReportColumn(columnName="保险金额",columnOrder=5)
	private BigDecimal amount;//总保险金额
	@ReportColumn(columnName="保险费",columnOrder=6)
	private BigDecimal prem;//保险费
	@ReportColumn(columnName="开航日期/起运日期",converter = true,columnOrder=7)
	private String sailingDate;//开航日期/起运日期
	@ReportColumn(columnName="装载运输工具名称",columnOrder=8)
	private String loadingName;//装载运输工具名称
	@ReportColumn(columnName="起运地",columnOrder=9)
	private String startPlace;//起运地
	@ReportColumn(columnName="目的地",columnOrder=10)
	private String endPlace;//目的地
	@ReportColumn(columnName="主条款",columnOrder=11)
	private String mainTerm;//主条款
	@ReportColumn(columnName="附加条款",columnOrder=12)
	private String addTerm;//附加条款
	@ReportColumn(columnName="赔付地点",columnOrder=13)
	private String claimPlace;//赔付地点
	@ReportColumn(columnName="提单号/运单号",columnOrder=14)
	private String billNo;//提单号/运单号
	@ReportColumn(columnName="发票号/合同号",columnOrder=15)
	private String contractNo;//发票号\合同号
	@ReportColumn(columnName="包装与数量",columnOrder=16)
	private String goodsQuantity;//包装与数量
	@ReportColumn(columnName="保险货物名称",columnOrder=17)
	private String goodsName;//保险货物名称
	@ReportColumn(columnName="发票金额",columnOrder=18)
	private String invoiceAmount;//发票金额
	@ReportColumn(columnName="免赔额",columnOrder=19)
	private String franchise;//责任免赔额
	@ReportColumn(columnName="运输方式",columnOrder=20)
	private String shippingType;//运输方式
	@ReportColumn(columnName="特别约定",columnOrder=21)
	private String specialAgreement;//特别约定
	@ReportColumn(columnName="船龄",columnOrder=22)
	private String vesselsAge;//船龄
	@ReportColumn(columnName="起保日期",columnOrder=23)
	private String policyStartTime;//保险起始时间
	@ReportColumn(columnName="终保日期",columnOrder=24)
	private String policyEndTime;//保险结束时间

	//日期展示转换
	public String convertSailingDate(Object entitie, String v) {
		if (null == v || "".equals(v) || "null".equals(v)) {
			return "";
		}
		if(v.length()<8){
			return v;
		}else{
			return v.substring(0, 4)+"/"+v.substring(4, 6)+"/"+v.substring(6, 8);
		}
	}
}
