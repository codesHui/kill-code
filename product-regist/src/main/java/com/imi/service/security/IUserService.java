package com.imi.service.security;

import com.imi.base.model.security.UserInfo;
import com.imi.base.service.IBaseDataService;
import com.imi.entity.security.User;
/**
 * 用户服务接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IUserService extends IBaseDataService<UserInfo> {
	/**
	 * 加载性别名称。
	 * @param gender
	 * 性别值。
	 * @return
	 * 性别名称。
	 */
	String loadGenderName(Integer gender);
	/**
	 * 加载用户类型名称。
	 * @param type
	 * 用户类型。
	 * @return
	 * 用户类型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 加载用户状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 数据模型转换。
	 * @param data
	 * 用户数据。
	 * @param isViewPwd
	 * 是否显示明文密码。
	 * @return
	 */
	UserInfo conversion(User data,boolean isViewPwd);
	/**
	 * 更新用户。
	 * @param info
	 * 用户信息。
	 * @return
	 * 用户数据。
	 */
	User updateUser(UserInfo info);
	/**
	 * 修改用户密码。
	 * @param userId
	 * 用户ID。
	 * @param oldPassword
	 * 旧密码。
	 * @param newPassword
	 * 新密码。
	 * @throws Exception
	 */
	void modifyPassword(Long userId,String oldPassword,String newPassword) throws Exception;
	/**
	 * 初始化用户。
	 * @param roleId
	 * 角色ID。
	 * @param account
	 * 账号。
	 * @param password
	 * 密码。
	 * @throws Exception
	 */
	void init(Long roleId,String account, String password) throws Exception;
	/**
	 * 删除用户。
	 * @param userId
	 */
	void deleteUser(String userId);
	
	User findById(Long id);
	
	User findByUserCode(String usercode);
}