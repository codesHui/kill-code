package com.imi.service.security;

import java.util.Set;

import com.imi.entity.security.User;

/**
 * 用户授权接口。
 * 
 * @author josh
 * @since 2014年11月3日
 */
public interface IUserAuthorization {
	/**
	 * 根据账号加载用户。
	 * @param account
	 * 用户账号。
	 * @return
	 * 用户信息。
	 */
	User loadUserByAccount(String account);
	/**
	 * 根据账号查找用户角色ID集合。
	 * @param account
	 * 用户账号。
	 * @return
	 * 角色ID集合。
	 */
	Set<String> findRolesByAccount(String account);
	/**
	 * 根据账号查询其权限集合。
	 * @param account
	 * 用户账号。
	 * @return
	 * 权限集合。
	 */
	Set<String> findPermissionsByAccount(String account);
}