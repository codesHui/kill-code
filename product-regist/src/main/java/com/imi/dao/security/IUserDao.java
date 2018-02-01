package com.imi.dao.security;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.security.UserInfo;
import com.imi.entity.security.User;

/**
 * 用户数据接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IUserDao extends IBaseDao<User> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<User> findUsers(UserInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(UserInfo info);
	/**
	 * 根据账号查找用户。
	 * @param account
	 * 账号。
	 * @return
	 * 用户。
	 */
	 User findByAccount(String account);
	 
	 User findById(Long id);
}