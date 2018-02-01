package com.softvan.utils;

import org.springframework.util.StringUtils;

/**
 * 字符串工具类。
 */
public final class StringUtil {
	/**
	 * 拼接字符串。
	 * @param arrays
	 * 字符串数据。
	 * @param split
	 * 分隔符。
	 * @return
	 */
	public static String join(String[] arrays, Character split){
		if(arrays == null || arrays.length == 0) return null;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < arrays.length; i++){
			if(StringUtils.isEmpty(arrays[i])) continue;
			if(builder.length() > 0) builder.append(split);
			builder.append(arrays[i]);
		}
		return builder.toString();
	}
	/**
	 * 转半角。
	 * 全角空格为12288，半角空格为32,其他半角字符（33-126）与全角（65281-65374）的对应关系是：均相差65248
	 * @param source
	 * @return
	 */
	public static String toSemiangle(String source){
		if(StringUtils.isEmpty(source)) return source;
		final int sbc_char_start = 65281,/*全角字符开始*/
					sbc_char_end = 65374,/*全角字符结束*/
					offset = 65248,/*与半角的偏移量*/
					sbc_space = 12288,/*全角空格*/
					dbc_space = 32;/*半角空格*/
		char[] chars = source.toCharArray();
		for(int i = 0; i < chars.length; i++){
			if(chars[i] == sbc_space){
				chars[i] = dbc_space;
			}else if(chars[i] >= sbc_char_start && chars[i] <= sbc_char_end){
				chars[i] = (char)(chars[i] - offset);
			}
		}
		return String.valueOf(chars);
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str){
		if(null == str || "".equals(str) || "null".equals(str)){
			return false;
		}
		return true;
	}
}