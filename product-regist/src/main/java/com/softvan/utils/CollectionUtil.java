package com.softvan.utils;

import java.util.List;

public class CollectionUtil {
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(List list){
		if(null == list || list.size() <=0 ){
			return false;
		}
		return true;
	}
}
