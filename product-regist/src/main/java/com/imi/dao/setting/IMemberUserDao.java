package com.imi.dao.setting;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;

/**
 * 用户数据接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IMemberUserDao extends IBaseDao<MemberUser> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<MemberUser> findUsers(MemberUserInfo info);
	/**
	 * 用户管理  用户查询
	 * @param info
	 * @return
	 */
	void findUsersList(MemberUserInfo info,String roleCode);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(MemberUserInfo info);
	/**
	 * 根据会员信息查找会员
	 * @param info
	 * @return
	 */
	MemberUser findByMemberUserinfo(MemberUserInfo info);
	
	public List<MemberUser> findUsersByUserId(Long userId);
	
	List<MemberUser> findAllUser();
}