package com.imi.service.security;
/**
 * 验证码类型。
 * 
 * @author josh
 * @since 2014年10月28日
 */
public enum VerifyCodeType {
	/**
	 * 验证码类型仅为数字，即0-9。
	 */
	NUM_ONLY(0),
	/**
	 * 验证码类型仅为字母，即大小写字母混合。
	 */
	LETTER_ONLY (1),
	/**
	 * 验证码类型为数字和大小写字母混合。
	 */
	ALL_MIXED(2),
	/**
	 * 验证码类型为数字和大写字母混合。
	 */
	NUM_UPPER(3),
	/**
	 * 验证码类型为数字和小写字母混合。
	 */
	NUM_LOWER(4),
	/**
	 * 验证码类型仅为大写字母。
	 */
	UPPER_ONLY(5),
	/**
	 * 验证码类型仅为小写字母。
	 */
	LOWER_ONLY(6);
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private VerifyCodeType(int value){
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
	public static VerifyCodeType conversion(Integer type){
		if(type == null) return VerifyCodeType.NUM_ONLY;
		for(VerifyCodeType verifyCodeType : VerifyCodeType.values()){
			if(verifyCodeType.getValue() == type) return verifyCodeType;
		}
		throw new RuntimeException(String.format("不能转换为验证码枚举对象［%d］！", type));
	}
}