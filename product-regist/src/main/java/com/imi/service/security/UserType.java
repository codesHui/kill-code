package com.imi.service.security;
/**
 * 用户类型枚举。
 * 
 * @author josh
 * @since 2014年11月10日
 */
public enum UserType {
	/**
	 * 后台用户。
	 */
	BACKGROUND(1),
	/**
	 * 前台用户。
	 */
	FRONT(2);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private UserType(int value){
		this.value = value;
	}
	/**
	 * 获取验证码类型值。
	 * @return 验证码类型值。
	 */
	public int getValue(){
		return this.value;
	}
	/**
	 * 将数字转换为美举值。
	 * @param type
	 * @return
	 */
	public static UserType conversion(Integer type){
		if(type == null) return UserType.BACKGROUND;
		for(UserType userType : UserType.values()){
			if(userType.getValue() == type) return userType;
		}
		throw new RuntimeException(String.format("不能转换为用户类型枚举对象［%d］！", type));
	}
}