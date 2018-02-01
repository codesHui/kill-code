package com.imi.controllers.icrm.policy;

public class PolicyConstant {
	
	//远洋船舶险不校验的列
	static String[] risk08OV_not_check = {"船舶登记号IMO","建造商","保险价值","船壳保险金额","船壳险费率","特别约定"};
	//保陪保险不校验的列
	static String[] risk05PI_not_check = {"每次事故责任限额","每次事故责任免赔额","特别约定","保险附加条款"};
	//进出口货运险不校验的列
	static String[] risk09IE_not_check = {"提单号/运单号","发票号/合同号","包装与数量","保险货物名称","发票金额","免赔额","运输方式","特别约定","船龄","起保日期","终保日期"};
	//集装箱保险不校验的列
	static String[] risk08CT_not_check = {"使用范围","承保区域","总保险价值","总费率","免赔额/率","付费方式","特别约定"};
	//船舶油污责任保险不校验的列
	static String[] risk05OP_not_check = {"船级","船舶用途","制造厂商","责任累计限额","保险附加条款","特别约定"};
	
	//远洋船舶险 数字校验
	static String[] risk08OV_num_check ={"保险金额","保险费率(%)","保费","附加险保费"};
	//保陪保险 数字校验
	static String[] risk05PI_num_check ={"累计责任限额","保险费"};
	//进出口货运险 数字校验
	static String[] risk09IE_num_check ={"保险金额","保险费"};
	//集装箱保险 数字校验
	static String[] risk08CT_num_check ={"标的数量","总保险金额","保险费"};
	//船舶油污责任保险 数字校验
	static String[] risk05OP_num_check ={"每次事故责任限额","每次事故免赔额","保险费"};
	//汇总 数字校验
	static String[] summary_num_check ={"保费金额(USD)","保费金额(JPY)","保费金额(EUR)","保费金额(GBP)","保费金额(CHF)","折币保费金额"};
	
	//远洋船舶险日期校验
	static String[] policy_date_check ={"起保日期","终保日期"};
	//进出口货运险日期校验
	static String[] vessel_date_check ={"开航日期/起运日期"};
	
	//币种校验
	static String[] currency_check ={"币种"};
	//年份校验
	static String[] year_check ={"年份"};
	//月份校验
	static String[] month_check ={"月份"};
	
	static String[] risk08OV_isOrNot_check={"境外船舶险"};
	
}
