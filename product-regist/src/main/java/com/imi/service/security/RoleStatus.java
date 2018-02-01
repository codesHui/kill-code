package com.imi.service.security;
/**
 * 角色状态枚举。
 * 
 * @author josh
 * @since 2014年11月3日
 */
public enum RoleStatus {
	/**
	 * 启用。
	 */
	ENABLED(1),
	/**
	 * 停用。
	 */
	DISABLE(0);
	
	private int value;
	/**
	 * 构造函数。
	 * @param value
	 */
	private RoleStatus(int value){
		this.value = value;
	}
	/**
	 * 获取角色状态值。
	 * @return 角色状态值。
	 */
	public int getValue() {
		return value;
	}
	/**
	 * 角色状态转换。
	 * @param value
	 * 角色状态值。
	 * @return
	 * 角色状态枚举对象。
	 */
	public static RoleStatus conversion(Integer value){
		if(value == null) throw new RuntimeException("角色状态值为空！");
		for(RoleStatus status : RoleStatus.values()){
			if(status.getValue() == value){
				return status;
			}
		}
		throw new RuntimeException(String.format("角色状态［%d］未定义！", value));
	}
	
}