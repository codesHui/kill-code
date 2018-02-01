package com.imi.service.security;

import java.util.List;

import com.imi.base.model.security.UserInfo;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
/**
 * 用户服务接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IMemberUserService {
	/**
	 * 更新用户。
	 * @param info
	 * 用户信息。
	 * @return
	 * 用户数据。
	 */
	void updateUser(MemberUser info);
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
	/**
	 * 查找用户。
	 * @param String
	 * 用户信息。
	 * @return
	 * 用户数据。
	 */
	MemberUser findMemberUser(MemberUserInfo info);
	MemberUser findMemberUserbyUserId(Long current_user_id);
	List<MemberUser> findMemberUsers(MemberUserInfo info);
	void findMemberUsersList(MemberUserInfo info,String roleCode);
	void saveOrUpdate(MemberUserInfo info,String roleName);
	Long totalNums(MemberUserInfo info);
}