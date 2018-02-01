package com.imi.service.security;

import com.imi.entity.security.Right;

/**
 *  默认权限类型枚举。
 * 
 * @author josh
 * @since 2014年10月31日
 */
public enum DefaultRightType {
	/**
	 * 查看权限。
	 */
	VIEW(Right.VIEW),
	/**
	 * 更新权限。
	 */
	UPDATE(Right.UPDATE),
	/**
	 * 删除权限。
	 */
	DELETE(Right.DELETE);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private DefaultRightType(int value){
		this.value = value;
	}
	/**
	 * 获取枚举值。
	 * @return
	 */
	public Integer getValue(){ 
		return this.value;
	};
	/**
	 * 默认权限枚举值转换。
	 * @param value
	 * 枚举值。
	 * @return
	 * 枚举对象。
	 */
	public static DefaultRightType convert(Integer value){
		if(value == null) throw new RuntimeException("枚举值为空！");
		for(DefaultRightType right : DefaultRightType.values()){
			if(right.getValue() == value) return right;
		}
		throw new RuntimeException(String.format("枚举值［%d］未定义！", value));
	}
}