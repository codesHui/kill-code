package com.imi.support;

import java.lang.reflect.Field;
import java.util.Comparator;

import com.imi.base.annotate.ReportColumn;

/**
 * EXCEL模板的列顺序排列器
 * 
 * @author lzeus Liu
 * 
 */
public class ColumnComparator implements Comparator<Field> {
	/**
	 * 比较注解的字段的COLUMN的顺序，小的显示在前，大的在后面
	 */
	public int compare(Field o1, Field o2) {
		ReportColumn annoOne = o1.getAnnotation(ReportColumn.class);
		ReportColumn annoTwo = o2.getAnnotation(ReportColumn.class);
		if (annoOne == null || annoTwo == null) {
			return 0;
		}
		return annoOne.columnOrder() - annoTwo.columnOrder();
	}
}