package com.imi.support;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.softvan.model.IEnum;

/**
 * 枚举Map工具类。
 */
@Component
public final class EnumMapUtils {

	private static Map<String, String> cacheMap = Maps.newHashMap();

	/**
	 * 获取枚举中文字段
	 * 
	 * @param source
	 * @return
	 */
	public static String convert(IEnum source) {
		if (source == null) {
			return "";
		}
		String key = source.getClass().getSimpleName() + "." + source;
		if (cacheMap.containsKey(key)) {
			return cacheMap.get(key);
		} else {
			cacheMap.put(
					key,
					MessageUtil.getMessage(source.getClass().getSimpleName()
							+ "." + source));
		}

		return cacheMap.get(key);
	}

	/**
	 * 根据美剧类型获取treeMap
	 * 
	 * @param <E>
	 * @param <E>
	 * 
	 * @param clazz
	 * @return
	 */
	public static <E> Map<String, String> treeMap(Class clazz) {

		if (clazz == null || (clazz.isAssignableFrom(IEnum.class))) {
			return Maps.newHashMap();
		}

		EnumSet<? extends IEnum> currEnumSet = EnumSet.allOf(clazz);
		Map<String, String> statusMap = Maps.newHashMap();
		for (IEnum inum : currEnumSet) {
		//	System.out.println(inum);
			String value = convert(inum);
		//	System.out.println(inum.toString());
			statusMap.put(String.format("%d", inum.getValue()), value);
		}
		return statusMap;

	}

	/**
	 * 创建以键名排序的TreeMap对象。
	 * 
	 * @return
	 */
	public static final Map<String, String> createTreeMap() {
		return new TreeMap<String, String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				Integer x = Integer.parseInt(o1), y = Integer.parseInt(o2);
				if (x == null || y == null) {
					return o1.compareToIgnoreCase(o2);
				}
				return x - y;
			}
		});
	}

}