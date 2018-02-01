package com.softvan.model;
/**
 * 当前用户接口。
 */
public interface IUser {
	/**
	 * 获取当前用户ID。
	 * @return 当前用户ID。
	 */
	Long getCurrentUserId();
	/**
	 * 设置当前用户ID。
	 * @param currentUserId
	 * 当前用户ID。
	 */
	void setCurrentUserId(Long currentUserId);
}