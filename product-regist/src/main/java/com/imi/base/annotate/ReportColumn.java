package com.imi.base.annotate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel BEAN配置注解 配置报表列 使用说明: 
 * 1.将BEAN中需要导出的字段以该注解配置 
 * 2.String columnName()为列的名字标识Excel中一个列的显示名称 
 * 3.int columnOrder()定义列的显示顺序
 * 4.entities子对象的属性表方法：对象字段+"_"+子对象字段如user.id表示为user_id
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReportColumn {
	/**
	 * Excel列名
	 * 
	 * @return
	 */
	String columnName();
	
	boolean  converter() default false;




	/**
	 * Excel显示顺序排序字段
	 * 
	 * @return
	 */
	int columnOrder();
}
