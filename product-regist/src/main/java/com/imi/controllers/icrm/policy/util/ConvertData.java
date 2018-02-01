package com.imi.controllers.icrm.policy.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.imi.controllers.icrm.policy.PolicyCheck;
import com.imi.controllers.icrm.policy.constant.Risk_05OP_ENUM;
import com.imi.controllers.icrm.policy.constant.Risk_05PI_ENUM;
import com.imi.controllers.icrm.policy.constant.Risk_08CT_ENUM;
import com.imi.controllers.icrm.policy.constant.Risk_08OV_ENUM;
import com.imi.controllers.icrm.policy.constant.Risk_09IE_ENUM;
import com.imi.controllers.icrm.policy.constant.SUMMARY_ENUM;
import com.imi.entity.reportPolicy.ReportPolicy;
import com.imi.entity.reportPolicy.ReportSummary;
import com.softvan.utils.DateUtil;

public class ConvertData {
	
	public  static void main(String[] args){
		String e1 = "(\\d\\d\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])";
		if(isMatch("2017/09/01",e1)){
			String[] dStr = new String[3]; 
			dStr = "2017/09/01".split("/");
			System.out.println( dStr[0]+dStr[1]+dStr[2] );
		}
	}
	
	 private static boolean isMatch(String date, String e){
	    	Pattern p = Pattern.compile(e);   
	        Matcher m = p.matcher(date);  
	        return m.matches();
	    }
	
	/**
	 * 转换数据基础方法
	 * @param cell
	 * @return
	 */
	public static String convertCellToArray(Cell cell){
		if( null == cell ){
			return "";
		}else{
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					//日期类型
					return DateUtil.convertDate(cell.getDateCellValue(), "yyyyMMdd");
	            }else{
	            	// 数值类型
	            	String str = String.valueOf(cell.getNumericCellValue()).trim();
	            	if(str.endsWith(".0")){
	            		return str.substring(0, str.lastIndexOf(".0"));
	            	}
	            	return str;
	            } 
			}
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String e1 = "(\\d\\d\\d\\d)/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])";
				if(isMatch(cell.getStringCellValue().trim(),e1)){
					String[] dStr = new String[3]; 
					dStr = cell.getStringCellValue().trim().split("/");
					return dStr[0]+dStr[1]+dStr[2];
				}else{
					// 字符串类型
					return cell.getStringCellValue().trim();
				}
			}
		}
		return "";
	}
	
	/**
	 * 反射数据到08OV -- 远洋船舶
	 * @param row
	 * @param length
	 * @param policyLst
	 * @return
	 */
	public static void convertDataToO80V(Row row, int length,
			List<ReportPolicy> policyLst,String registCode,List<String> policyNoLst) {
		ReportPolicy policy = new ReportPolicy();
		for (int i = 0; i < length; i++) {
			
			PolicyReflectEntityUtil entityUtil = new PolicyReflectEntityUtil(policy);  
	        for (Risk_08OV_ENUM tableField : Risk_08OV_ENUM.values()) {  
	            //根据Enum上属性名称拿到属性的值，这里所有的属性值都是一个Object类型。  
	        	if(Risk_08OV_ENUM.getPropertyByIndex(i) == tableField.toString()){
	 	            try {  
	 	            	
	 	            	String obj = convertCellToArray(row.getCell(i));
	 	                //传入属性名和值，完成自动填充BO属性。  
	 	            	if(Risk_08OV_ENUM.Amount.toString().equals(tableField.toString())||
	 	            	   Risk_08OV_ENUM.Prem.toString().equals(tableField.toString())||
	 	            	   Risk_08OV_ENUM.AddPrem.toString().equals(tableField.toString())){
	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj)); 
	 	            	}else if(Risk_08OV_ENUM.PolicyRate.toString().equals(tableField.toString())){
	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj).setScale(4));
	 	            	}else{
	 	            		entityUtil.doInitEntity(tableField.toString(), obj); 
	 	            	} 
	 	            	
	 	            	//临时存储保单号
	 	            	if(Risk_08CT_ENUM.PolicyNo.toString().equals(tableField.toString())){
	 	            		policyNoLst.add(obj);
	 	            	}
	 	            } catch (Exception e) {  
	 	                e.printStackTrace();  
	 	            }  
	        	}
	        }  
		}
		policy.setRiskName("远洋船舶险");//险种名称 
		policy.setRiskType("08OV");//险种类型 
		policy.setPolicyUploadUnit(registCode);//保险公司上报会员 
		policy.setCreatedTime(new Date());
		policy.setModifiedTime(new Date());
        policyLst.add(policy);
	}
	
	/**
	 * 反射数据到05PI -- 保赔
	 * @param row
	 * @param length
	 * @param policyLst
	 * @return
	 */
	public static void convertDataTo05PI(Row row, int length,
			List<ReportPolicy> policyLst,String registCode,List<String> policyNoLst) {
		ReportPolicy policy = new ReportPolicy();
		for (int i = 0; i < length; i++) {
			
			PolicyReflectEntityUtil entityUtil = new PolicyReflectEntityUtil(policy);  
	        for (Risk_05PI_ENUM tableField : Risk_05PI_ENUM.values()) {  
	            //根据Enum上属性名称拿到属性的值，这里所有的属性值都是一个Object类型。  
	        	if(Risk_05PI_ENUM.getPropertyByIndex(i) == tableField.toString()){
	 	            try {  
	 	            	String obj = convertCellToArray(row.getCell(i));
	 	                //传入属性名和值，完成自动填充BO属性。  
	 	            	if(Risk_05PI_ENUM.TotalAccidentLimit.toString().equals(tableField.toString())||
	 	            	   Risk_05PI_ENUM.Prem.toString().equals(tableField.toString())){
   	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj)); 
   	 	            	}else{
   	 	            		entityUtil.doInitEntity(tableField.toString(), obj); 
   	 	            	}  
	 	            	//临时存储保单号
	 	            	if(Risk_08CT_ENUM.PolicyNo.toString().equals(tableField.toString())){
	 	            		policyNoLst.add(obj);
	 	            	}
	 	            } catch (Exception e) {  
	 	                e.printStackTrace();  
	 	            }  
	        	}
	        } 
	        
		}
		policy.setRiskName("保赔保险");//险种名称 
		policy.setRiskType("05PI");//险种类型 
		policy.setPolicyUploadUnit(registCode);//保险公司上报会员
		policy.setCreatedTime(new Date());
		policy.setModifiedTime(new Date());
        policyLst.add(policy);
	}
	
	/**
	 * 反射数据到09IE --进出口
	 * @param row
	 * @param length
	 * @param policyLst
	 * @return
	 */
	public static void convertDataTo09IE(Row row, int length,
			List<ReportPolicy> policyLst,String registCode,List<String> policyNoLst) {
		ReportPolicy policy = new ReportPolicy();
		for (int i = 0; i < length; i++) {
			PolicyReflectEntityUtil entityUtil = new PolicyReflectEntityUtil(policy);  
	        for (Risk_09IE_ENUM tableField : Risk_09IE_ENUM.values()) {  
	            //根据Enum上属性名称拿到属性的值，这里所有的属性值都是一个Object类型。  
	        	if(Risk_09IE_ENUM.getPropertyByIndex(i) == tableField.toString()){
	 	            try {  
	 	            	
	 	            	String obj = convertCellToArray(row.getCell(i));

	 	                //传入属性名和值，完成自动填充BO属性。  
	 	            	if(Risk_09IE_ENUM.Amount.toString().equals(tableField.toString())||
 	            		   Risk_09IE_ENUM.Prem.toString().equals(tableField.toString())){
  	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj)); 
  	 	            	}else{
  	 	            		entityUtil.doInitEntity(tableField.toString(), obj); 
  	 	            	} 
	 	            	
	 	            	//临时存储保单号
	 	            	if(Risk_08CT_ENUM.PolicyNo.toString().equals(tableField.toString())){
	 	            		policyNoLst.add(obj);
	 	            	}
	 	            } catch (Exception e) {  
	 	                e.printStackTrace();  
	 	            }  
	        	}
	        }  
		}
        policy.setRiskName("进出口货运险");//险种名称 
		policy.setRiskType("09IE");//险种类型 
		policy.setPolicyUploadUnit(registCode);//保险公司上报会员
		policy.setCreatedTime(new Date());
		policy.setModifiedTime(new Date());
        policyLst.add(policy);
	}
	
	/**
	 * 反射数据到08CT -- 集装箱
	 * @param row
	 * @param length
	 * @param policyLst
	 * @return
	 */
	public static void convertDataTo08CT(Row row, int length,
			List<ReportPolicy> policyLst,String registCode,List<String> policyNoLst) {
		ReportPolicy policy = new ReportPolicy();
		for (int i = 0; i < length; i++) {		
			PolicyReflectEntityUtil entityUtil = new PolicyReflectEntityUtil(policy);  
	        for (Risk_08CT_ENUM tableField : Risk_08CT_ENUM.values()) {  
	            //根据Enum上属性名称拿到属性的值，这里所有的属性值都是一个Object类型。  
	        	if(Risk_08CT_ENUM.getPropertyByIndex(i) == tableField.toString()){
	 	            try {  
	 	            	String obj = convertCellToArray(row.getCell(i));
	 	                //传入属性名和值，完成自动填充BO属性。  
	 	            	if(Risk_08CT_ENUM.InsuredMarkQuantity.toString().equals(tableField.toString())||
	 	            	   Risk_08CT_ENUM.Amount.toString().equals(tableField.toString())||
	 	            	   Risk_08CT_ENUM.Prem.toString().equals(tableField.toString())){
 	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj)); 
 	 	            	}else{
 	 	            		entityUtil.doInitEntity(tableField.toString(), obj); 
 	 	            	}
	 	            	
	 	            	//临时存储保单号
	 	            	if(Risk_08CT_ENUM.PolicyNo.toString().equals(tableField.toString())){
	 	            		policyNoLst.add(obj);
	 	            	}
	 	            } catch (Exception e) {  
	 	                e.printStackTrace();  
	 	            }  
	        	}
	        }  
		}
		policy.setRiskName("集装箱保险");//险种名称 
		policy.setRiskType("08CT");//险种类型 
		policy.setPolicyUploadUnit(registCode);//保险公司上报会员 
		policy.setCreatedTime(new Date());
		policy.setModifiedTime(new Date());
        policyLst.add(policy);
	}
	
	/**
	 * 反射数据到05OP -- 船舶油污
	 * @param row
	 * @param length
	 * @param policyLst
	 * @return
	 */
	public static void convertDataTo05OP(Row row, int length,
			List<ReportPolicy> policyLst,String registCode,List<String> policyNoLst) {
		ReportPolicy policy = new ReportPolicy();
		for (int i = 0; i < length; i++) {
			PolicyReflectEntityUtil entityUtil = new PolicyReflectEntityUtil(policy);  
	        for (Risk_05OP_ENUM tableField : Risk_05OP_ENUM.values()) {  
	            //根据Enum上属性名称拿到属性的值，这里所有的属性值都是一个Object类型。  
	        	if(Risk_05OP_ENUM.getPropertyByIndex(i) == tableField.toString()){
	 	            try {  
	 	            	String obj = convertCellToArray(row.getCell(i));
	 	                //传入属性名和值，完成自动填充BO属性。  
	 	            	if(Risk_05OP_ENUM.AccidentLimit.toString().equals(tableField.toString())||
	 	            	   Risk_05OP_ENUM.Franchise_05OP.toString().equals(tableField.toString())||
	 	            	   Risk_05OP_ENUM.Prem.toString().equals(tableField.toString())){
	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj)); 
	 	            	}else{
	 	            		entityUtil.doInitEntity(tableField.toString(), obj); 
	 	            	}
	 	            	//临时存储保单号
	 	            	if(Risk_05OP_ENUM.PolicyNo.toString().equals(tableField.toString())){
	 	            		policyNoLst.add(obj);
	 	            	}
	 	            } catch (Exception e) {  
	 	                e.printStackTrace();  
	 	            }  
	        	}
				
	        }  
		}
		policy.setRiskName("船舶油污责任保险");//险种名称
		policy.setRiskType("05OP");//险种类型 
		policy.setPolicyUploadUnit(registCode);//保险公司上报会员
		policy.setCreatedTime(new Date());
		policy.setModifiedTime(new Date());
        policyLst.add(policy);
	}

	/**
	 * 汇总信息反射
	 * @param row
	 * @param maxCellCount
	 * @param summaryLst
	 * @param registCode
	 * @return
	 */
	public static void convertDataToSummary(Sheet sheet,Row row,int maxCellCount,
			 List<ReportSummary> summaryLst,String registCode) {
		
		String result = PolicyCheck.checkSummaryAllNull(sheet);
		if("".equals(result)){
			//汇总表无数据
			return;
		}
		
		ReportSummary summary = new ReportSummary();
		for (int i = 0; i < maxCellCount; i++) {
			//反射
			SummaryReflectEntityUtil entityUtil = new SummaryReflectEntityUtil(summary);  
	        for (SUMMARY_ENUM tableField : SUMMARY_ENUM.values()) {  
	            //根据Enum上属性名称拿到属性的值，这里所有的属性值都是一个Object类型。  
	        	if(SUMMARY_ENUM.getPropertyByIndex(i) == tableField.toString()){
	        		 String obj = convertCellToArray(row.getCell(i));
	 	            try {  
	 	                //传入属性名和值，完成自动填充BO属性。  
	 	            	if(SUMMARY_ENUM.RiskName.toString().equals(tableField.toString())){
	 	            		String riskType = "";
	 	            		if("远洋船舶险".equals(obj)){ riskType ="08OV";}
	 	            		if("保赔保险".equals(obj)){ riskType = "05PI";}
	 	            		if("进出口货运险".equals(obj)){ riskType = "09IE";}
	 	            		if("集装箱保险".equals(obj)){ riskType = "08CT";}
	 	            		if("船舶油污责任保险".equals(obj)){ riskType = "05OP";}
	 	            		entityUtil.doInitEntity("RiskType", riskType); 
	 	            	}
	 	            	//数字类型存储
	 	            	if(SUMMARY_ENUM.PremUSD.toString().equals(tableField.toString()) ||
	 	            			SUMMARY_ENUM.PremJPY.toString().equals(tableField.toString())||
	 	            			SUMMARY_ENUM.PremEUR.toString().equals(tableField.toString())||
	 	            			SUMMARY_ENUM.PremGBP.toString().equals(tableField.toString())||
	 	            			SUMMARY_ENUM.PremCHF.toString().equals(tableField.toString())||
	 	            			SUMMARY_ENUM.Prem.toString().equals(tableField.toString())){
	 	            		entityUtil.doInitEntity(tableField.toString(), new BigDecimal(obj)); 
	 	            	}else{
	 	            		entityUtil.doInitEntity(tableField.toString(), obj); 
	 	            	}
	 	            	
	 	            } catch (Exception e) {  
	 	                e.printStackTrace();  
	 	            }  
	        	}
	        }  
		}
		summary.setReportUnit(registCode); 
		summary.setCreatedTime(new Date());
		summary.setModifiedTime(new Date());
		summaryLst.add(summary);
	}
}
