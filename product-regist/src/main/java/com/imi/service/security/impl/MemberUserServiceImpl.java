package com.imi.service.security.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.imi.dao.security.IRoleDao;
import com.imi.dao.security.IUserDao;
import com.imi.dao.setting.IMemberUserDao;
import com.imi.entity.products.Product;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
import com.imi.model.product.ProductInfo;
import com.imi.service.security.IMemberUserService;
import com.imi.service.setting.ReceiverService;

/**
 * 用户服务接口实现。
 */
@Service
public class MemberUserServiceImpl implements IMemberUserService {
	private static final Logger logger = Logger.getLogger(MemberUserServiceImpl.class);
	@Resource
	private IMemberUserDao memberUserDao;
	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private ReceiverService receiverService;

	/*
	 * 修改密码。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#modifyPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modifyPassword(Long userId,String oldPassword,String newPassword) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新用户［%1$s］密码:［%1$s］ =>［％2$s］", userId,oldPassword,newPassword));
		MemberUser info = new MemberUser();
	}
	/*
	 * 根据账号加载用户。
	 * @see com.softvan.netplatform.service.admin.security.IUserAuthorization#loadUserByAccount(java.lang.String)
	 */
	@Override
	public MemberUser findMemberUser(MemberUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号［%s］加载用户...", info.getRegisteredCode()));
		return this.memberUserDao.findByMemberUserinfo(info);
	}
	@Override
	public void updateUser(MemberUser info) {
		this.memberUserDao.update(info);
	}
	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}
	public void init(Long roleId, String account, String password)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public MemberUser findMemberUserbyUserId(Long current_user_id) {
		MemberUserInfo memberUserInfo = new MemberUserInfo();
	    User user=  new User();
	    user.setId(current_user_id);
	    memberUserInfo.setUser(user);
		return findMemberUser(memberUserInfo);
	}
	@Override
	public List<MemberUser> findMemberUsers(MemberUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.memberUserDao.findUsers(info);
	}
	
	@Override
	public void findMemberUsersList(MemberUserInfo info,String roleCode) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		this.memberUserDao.findUsersList(info,roleCode);
	}
	@Override
	public void saveOrUpdate(MemberUserInfo info,String roleName)   {
		
		Set<Role> roles = null;
		if(roleName != null){
			roles = new HashSet<Role>();
			Role role = roleDao.findUnique("select r from Role r where r.code=?", roleName);
			if(role != null)roles.add(role);
		} 
		MemberUser muser = new MemberUser();
		muser.setId(info.getId());
		User user = new User();
		user = info.getUser();
		user.setName(user.getAccount());
		user.setRoles(roles);
		user.setNickName(info.getCompanyShortName());
		user.setEmail(info.getSpecialMail());
		User creator = new User();
		creator.setId(info.getCurrentUserId());
		user.setCreator(creator);
		if(muser.getId() != null && muser.getId() > 0){
			muser = memberUserDao.findByMemberUserinfo(info);
			userDao.update(user);
		}
		else{
			userDao.save(user);
		}
		muser.setUser(user);
		muser.setCompanyName(info.getCompanyName());
		muser.setCompanyShortName(info.getCompanyShortName());
		muser.setCompanyWebsit(info.getCompanyWebsit());
		muser.setSpecialMail(info.getSpecialMail());
		muser.setOrganizationCode(info.getOrganizationCode());
		muser.setRegisteredCode(info.getRegisteredCode());
		muser.setLegalPerson(info.getLegalPerson());
		muser.setContactPerson1Name(info.getContactPerson1Name());
		muser.setContactPerson1Phone(info.getContactPerson1Phone());
		muser.setContactPerson1Email(info.getContactPerson1Email());
		muser.setContactPerson2Name(info.getContactPerson2Name());
		muser.setContactPerson2Phone(info.getContactPerson2Phone());
		muser.setContactPerson2Email(info.getContactPerson2Email());
		muser.setRegisteredPerson(info.getCompanyName());
		muser.setRegisteredDate(String.valueOf(new Date()));
		
		if(logger.isDebugEnabled()) logger.debug("新增或更新数据...");
		if(muser.getId()== null){
			try {
				this.memberUserDao.save(muser);
				String title = "用户注册";
				String text = "欢迎使用航运保险产品注册平台，贵公司的用户权限已开通。";
				receiverService.insertMess(title, text, info.getCurrentUserId(), muser.getUser().getId());
				String title1 = "用户注册";
				String text1 = "欢迎使用航运保险产品注册平台，贵协会的用户权限已开通。";
				receiverService.insertMess(title1, text1, info.getCurrentUserId(), info.getCurrentUserId());
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		else{
			try {
				this.memberUserDao.update(muser);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String title = "用户信息更改";
				String text = "贵公司的用户信息变更成功。";
				receiverService.insertMess(title, text, info.getCurrentUserId(), muser.getUser().getId());
				String title1 = "用户注册";
				String text1 =muser.getCompanyName()+"公司的用户信息于"+format.format(new Date())+"发生变更";
				receiverService.insertMess(title1, text1, info.getCurrentUserId(), info.getCurrentUserId());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public Long totalNums(MemberUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("统计数据...");
		return this.memberUserDao.total(info);
	}
}