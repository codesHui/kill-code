package com.imi.controllers.icrm.policy;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.softvan.utils.DateUtil;

/**
 * 上传的保单信息的合法性的校验
 * @author Devon
 *
 */
public class PolicyCheck {
    
	/**
	 * 数字校验
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		try{
			BigDecimal tempStr = new BigDecimal(str);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	private static String convertDateFromCell(Cell cell){
		if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			return cell.getStringCellValue();
		}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				//日期类型
				return DateUtil.convertDate(cell.getDateCellValue(), "yyyy/MM/dd");
            }
			return String.valueOf(cell.getNumericCellValue());
		}
		return "";
	}
		
	/**
	 * 远洋船舶险校验
	 * @param title
	 * @param sheet
	 * @param k
	 * @param cell
	 * @param wrongMsg
	 * @param isWriteFlag
	 * @param canInsertDBFlag
	 * @return
	 */
	public static Map<String,Object> checkRisk08OV(Sheet sheet,int k,Cell cell, String wrongMsg, boolean isWriteFlag, boolean canInsertDBFlag){
		 Map<String,Object> result = new HashMap<String,Object>();
		 String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
		 if(ArrayUtils.contains(PolicyConstant.risk08OV_num_check,title)){
			 //保险金额,保险费率,保费,附加险保费 必须为数字
			 if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				 if(!isNumber(cell.getStringCellValue())){
					 wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
						isWriteFlag = false;
						canInsertDBFlag = false;
				 }
			 }else if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if(ArrayUtils.contains(PolicyConstant.policy_date_check,title)){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				sdf.parse(convertDateFromCell(cell));
			} catch (ParseException e) {
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--日期格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		if( ArrayUtils.contains(PolicyConstant.currency_check,title)){
			if(!checkCurrency(cell)){
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--币种不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		if( ArrayUtils.contains(PolicyConstant.risk08OV_isOrNot_check,title)){
			if(!checkIsOrNot(cell)){
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--类型不合规(请录入是/否)\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		result.put("wrongMsg", wrongMsg);
		result.put("isWriteFlag", isWriteFlag);
		result.put("canInsertDBFlag", canInsertDBFlag);
		return result; 
	}
	
	/**
	 * 保陪保险
	 * @param sheet
	 * @param k
	 * @param cell
	 * @param wrongMsg
	 * @param isWriteFlag
	 * @param canInsertDBFlag
	 * @return
	 */
	public static Map<String,Object> checkRisk05PI(Sheet sheet,int k,Cell cell, String wrongMsg, boolean isWriteFlag, boolean canInsertDBFlag){
		Map<String,Object> result = new HashMap<String,Object>();
		String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
		if( ArrayUtils.contains(PolicyConstant.policy_date_check,title) ){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				sdf.parse(convertDateFromCell(cell));
			} catch (ParseException e) {
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--日期格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.risk05PI_num_check,title)){
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				 if(!isNumber(cell.getStringCellValue())){
					 wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
						isWriteFlag = false;
						canInsertDBFlag = false;
				 }
			 }if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.currency_check,title) ){
			if(!checkCurrency(cell)){
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--币种不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		result.put("wrongMsg", wrongMsg);
		result.put("isWriteFlag", isWriteFlag);
		result.put("canInsertDBFlag", canInsertDBFlag);
		return result; 
	}
	
	/**
	 * 进出口货运险校验
	 * @param sheet
	 * @param k
	 * @param cell
	 * @param wrongMsg
	 * @param isWriteFlag
	 * @param canInsertDBFlag
	 * @return
	 */
	public static Map<String,Object> checkRisk09IE(Sheet sheet,int k,Cell cell, String wrongMsg, boolean isWriteFlag, boolean canInsertDBFlag){
		Map<String,Object> result = new HashMap<String,Object>();
		String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
		if( ArrayUtils.contains(PolicyConstant.vessel_date_check,title) ){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				sdf.parse(convertDateFromCell(cell));
			} catch (ParseException e) {
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--日期格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.risk09IE_num_check,title) ){
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				 if(!isNumber(cell.getStringCellValue())){
					 wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
						isWriteFlag = false;
						canInsertDBFlag = false;
				 }
			 }if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.currency_check,title) ){
			if(!checkCurrency(cell)){
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--币种不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		result.put("wrongMsg", wrongMsg);
		result.put("isWriteFlag", isWriteFlag);
		result.put("canInsertDBFlag", canInsertDBFlag);
		return result; 
	}
	
	/**
	 * 集装箱保险校验
	 * @param sheet
	 * @param k
	 * @param cell
	 * @param wrongMsg
	 * @param isWriteFlag
	 * @param canInsertDBFlag
	 * @return
	 */
	public static Map<String,Object> checkRisk08CT(Sheet sheet,int k,Cell cell, String wrongMsg, boolean isWriteFlag, boolean canInsertDBFlag){
		Map<String,Object> result = new HashMap<String,Object>();
		String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
		if( ArrayUtils.contains(PolicyConstant.policy_date_check,title) ){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				sdf.parse(convertDateFromCell(cell));
			} catch (ParseException e) {
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--日期格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.risk08CT_num_check,title)){
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				 if(!isNumber(cell.getStringCellValue())){
					 wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
						isWriteFlag = false;
						canInsertDBFlag = false;
				 }
			 }if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.currency_check,title) ){
			if(!checkCurrency(cell)){
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--币种不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		result.put("wrongMsg", wrongMsg);
		result.put("isWriteFlag", isWriteFlag);
		result.put("canInsertDBFlag", canInsertDBFlag);
		return result; 
	}
	
	/**
	 * 船舶油污责任险校验
	 * @param sheet
	 * @param k
	 * @param cell
	 * @param wrongMsg
	 * @param isWriteFlag
	 * @param canInsertDBFlag
	 * @return
	 */
	public static Map<String,Object> checkRisk05OP(Sheet sheet,int k,Cell cell, String wrongMsg, boolean isWriteFlag, boolean canInsertDBFlag){
		Map<String,Object> result = new HashMap<String,Object>();
		String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
		if( ArrayUtils.contains(PolicyConstant.policy_date_check,title) ){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				sdf.parse(convertDateFromCell(cell));
			} catch (ParseException e) {
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--日期格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.risk05OP_num_check,title) ){
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				 if(!isNumber(cell.getStringCellValue())){
					 wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
						isWriteFlag = false;
						canInsertDBFlag = false;
				 }
			 }if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.currency_check,title) ){
			if(!checkCurrency(cell)){
				wrongMsg = wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--币种不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		result.put("wrongMsg", wrongMsg);
		result.put("isWriteFlag", isWriteFlag);
		result.put("canInsertDBFlag", canInsertDBFlag);
		return result; 
	}
		
	/**
	 * 汇总表校验
	 * @param sheet
	 * @param k
	 * @param cell
	 * @param wrongMsg
	 * @param isWriteFlag
	 * @param canInsertDBFlag
	 * @return
	 */
	public static Map<String,Object> checkSummary(Sheet sheet,int k,Cell cell, String wrongMsg, boolean isWriteFlag, boolean canInsertDBFlag){
		
		Map<String,Object> result = new HashMap<String,Object>();
		String title = sheet.getRow(0).getCell(k).getStringCellValue().trim();
		if( ArrayUtils.contains(PolicyConstant.year_check,title)){
			if(!checkYear(cell)){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--年份不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		if( ArrayUtils.contains(PolicyConstant.month_check,title) ){
			if(!checkMonth(cell)){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--月份不合规\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}
		
		if( ArrayUtils.contains(PolicyConstant.summary_num_check,title)){
			if(cell.getCellType() == Cell.CELL_TYPE_STRING){
				 if(!isNumber(cell.getStringCellValue())){
					 wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
						isWriteFlag = false;
						canInsertDBFlag = false;
				 }
			 }if(cell.getCellType() != Cell.CELL_TYPE_NUMERIC){
				wrongMsg =  wrongMsg + "'"+sheet.getRow(0).getCell(k) + "'--数字格式不正确\n";
				isWriteFlag = false;
				canInsertDBFlag = false;
			}
		}

		result.put("wrongMsg", wrongMsg);
		result.put("isWriteFlag", isWriteFlag);
		result.put("canInsertDBFlag", canInsertDBFlag);
		return result; 
	}
	
	/**
	 * 月份校验
	 * @param cell
	 * @return
	 */
	private static boolean checkYear(Cell cell){
		//规定的币种
		String[] yearArray = {"2015","2016","2017","2018","2019","2020","2021",
							   "2022","2023","2024","2025","2026","2027","2028",
							   "2029","2030","2031","2032","2033","2034","2035"};
		String year="";
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			year = String.valueOf(cell.getNumericCellValue());
			if(year.endsWith(".0")){
				year = year.substring(0, year.lastIndexOf(".0"));
			}
		}
		if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			year = cell.getStringCellValue().trim();
		}
		if(!ArrayUtils.contains(yearArray,year)){
			return false;
		}
		return true;
	}
	
	/**
	 * 币种校验
	 * @param cell
	 * @return
	 */
	public static boolean checkCurrency(Cell cell){
		//规定的币种
		String[] currencyArray = {
				"AED","ALL","AOA","ARS","AUD","BAM","BGN","BHD",
				"BND","BOB","BRL","BWP","BYN","CAD","CHF","CLP",
				"CNY","COP","CZK","DKK","DZD","EGP","EUR","GBP",
				"GHS","GYD","HKD","HRK","HUF","IDR","ILS","INR",
		        "IQD","IRR","ISK","JOD","JPY","KES","KRW","KWD",
		        "KZT","LAK","LBP","LKR","LYD","MAD","MDL","MKD",
		        "MMK","MNT","MOP","MUR","MVR","MWK","MXN","MYR",
		        "NGN","NOK","NPR","NZD","OMR","PEN","PHP","PKR",
		        "PLN","PYG","QAR","RON","RSD","RUB","SAR","SDG",
		        "SDR","SEK","SGD","SLL","SRD","SSP","SYP","THB",
		        "TND","TRY","TWD","TZS","UAH","UGX","USD","UYU",
		        "UZS","VEF","VND","XAF","YER","ZAR","ZMW"};

		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return false;
		}
		if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			if(!ArrayUtils.contains(currencyArray,cell.getStringCellValue().trim())){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 月份校验
	 * @param cell
	 * @return
	 */
	public static boolean checkMonth(Cell cell){
		//规定的币种
		String[] monthArray = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		
		String month="";
		
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			month = String.valueOf(cell.getNumericCellValue());
			if(month.endsWith(".0")){
				month = month.substring(0, month.lastIndexOf(".0"));
			}
		}
		if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			month = cell.getStringCellValue().trim();
		}
		
		if(!ArrayUtils.contains(monthArray,month)){
			return false;
		}
		return true;
	}
	
	/**
	 * 校验是/否
	 * @param cell
	 * @return
	 */
	public static boolean checkIsOrNot(Cell cell){
		String[] checkArray = {"是","否"};
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return false;
		}
		if(cell.getCellType() == Cell.CELL_TYPE_STRING){
			if(!ArrayUtils.contains(checkArray,cell.getStringCellValue().trim())){
				return false;
			}
		}
		return true;
	}

	/**
	 * 汇总表，可以全为空
	 * @param sheet
	 * @return
	 */
	public static String checkSummaryAllNull(Sheet sheet){
		String isNull = "";
		for(int i=1;i<6;i++){
			Row row = sheet.getRow(i);
			for(int j=1;j<9;j++){
				Cell cell = row.getCell(j);
				if(null == cell || cell.getCellType()== Cell.CELL_TYPE_BLANK){
					continue;
				}else{
					isNull = "isNotAllNull";
					break;
				}
			}
		}
		return isNull;
	}
	
	/**
	 * 校验某一行是空数据的
	 * @param row
	 * @param totalCell
	 * @return
	 */
	public static String checkRowAllNull(Row row,int totalCell){
		String isNull = "";
		for(int i=0;i<totalCell;i++){
			Cell cell = row.getCell(i);
			if(null == cell || cell.getCellType()== Cell.CELL_TYPE_BLANK){
				continue;
			}else{
				isNull = "isNotAllNull";
				break;
			}
		}
		return isNull;
	}
}
