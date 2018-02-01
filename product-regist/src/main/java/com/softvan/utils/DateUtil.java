package com.softvan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类。
 * */
public final class DateUtil {
	/**
	 * 取得指定日期所在周的第一天。
	 * @param date
	 * 	指定日期。
	 * @return
	 * 	所在周的第一天。
	 * */
	public static Date firstDayOfWeek(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());//monday
		return calendar.getTime();
	}
	/**
	 * 取得指定日期所在周的最后一天。
	 * @param date
	 * 	指定日期。
	 * @return
	 * 	所在周的最后一天。
	 * */
	public static Date lastDayOfWeek(Date date){
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);//sunday
		return calendar.getTime();
	}
	/**
	 * 获取当前日期是星期几。
	 *  @param date
	 *   星期几。
	 * */
	public static int DayOfWeekValue(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 获取当前日期是星期几。
	 *  @param date
	 *   星期几。
	 * */
	public static String DayOfWeekText(Date date){
		String[] weekTexts = {"", "日", "一", "二", "三", "四", "五", "六"};
		return  "星期" + weekTexts[DayOfWeekValue(date)];
	}
	
	/**
	 * 根据格式转换日期
	 * @param date
	 * @param patten
	 * @return
	 */
	public static Date convertDate(String date,String patten){
		SimpleDateFormat sdf = new SimpleDateFormat(patten);  
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * 根据格式转换日期
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String convertDate(Date date,String patten){
		SimpleDateFormat sdf = new SimpleDateFormat(patten);  
		return sdf.format(date);
	}
	/**
	 * 把20160512类型转换成2016/05/12
	 */
	public static String geshihuaDate(String patten){ 
		if(patten==null || "".equals(patten)){
			return null;
		}
		patten = new StringBuilder(patten).toString();     //先将字符串颠倒顺序  
        String str2 = "";  
        for(int i=0;i<patten.length();i++){  
            if(i*2+4>patten.length()){  
                str2 += patten.substring(i*2, patten.length());  
                break;  
            }
            if(i==0){
            	if("/".equals(patten.substring(4, 5))){
            		return patten;
            	}
            	str2 += patten.substring(i,4)+"/";  
            	continue;
            }
            if(i==1){
            	continue;
            }
            str2 += patten.substring(i*2, i*2+2)+"/";  
        }  
        if(str2.endsWith("/")){  
            str2 = str2.substring(0, str2.length()-1);  
        }  
        System.out.println("转换过后的时间格式");
        return str2;
	}
	
}