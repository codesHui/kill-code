package com.imi.service.security.impl;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.imi.base.model.security.UserInfo;
import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.base.shiro.IUserCache;
import com.imi.dao.security.IRoleDao;
import com.imi.dao.security.IUserDao;
import com.imi.entity.security.MenuRight;
import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.imi.service.security.IUserAuthorization;
import com.imi.service.security.IUserService;
import com.imi.service.security.UserType;
import com.imi.support.PasswordHelper;
import com.softvan.service.Gender;
import com.softvan.service.Status;

/**
 * 用户服务接口实现。
 */
public class UserServiceImpl extends BaseDataServiceImpl<User, UserInfo> implements IUserService,IUserAuthorization {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private IUserDao userDao;
	private IRoleDao roleDao; 
	private IUserCache userCache;
	private Map<Integer, String> genderNameMap,typeNameMap,statusNameMap;
	private PasswordHelper passwordHelper;
	/**
	 * 设置用户数据接口。
	 * @param userDao
	 * 用户数据接口。
	 */
	public void setUserDao(IUserDao userDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户数据接口...");
		this.userDao = userDao;
	}
	/**
	 * 设置角色数据接口。
	 * @param roleDao
	 * 角色数据接口。
	 */
	public void setRoleDao(IRoleDao roleDao) {
		if(logger.isDebugEnabled()) logger.debug("注入角色数据接口...");
		this.roleDao = roleDao;
	}
	/**
	 * 设置用户缓存。
	 * @param userCache 
	 *	  用户缓存。
	 */
	public void setUserCache(IUserCache userCache) {
		if(logger.isDebugEnabled()) logger.debug("注入用户缓存...");
		this.userCache = userCache;
	}
	/**
	 * 设置密码工具。
	 * @param passwordHelper
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		if(logger.isDebugEnabled()) logger.debug("注入密码工具...");
		this.passwordHelper = passwordHelper;
	}
	/**
	 * 设置性别名称集合。
	 * @param genderNameMap
	 * 性别名称集合。
	 */
	public void setGenderNameMap(Map<Integer, String> genderNameMap) {
		if(logger.isDebugEnabled()) logger.debug("注入性别名称集合...");
		this.genderNameMap = genderNameMap;
	}
	/**
	 * 设置用户类型名称集合。
	 * @param typeNameMap 
	 *	  用户类型名称集合。
	 */
	public void setTypeNameMap(Map<Integer, String> typeNameMap) {
		if(logger.isDebugEnabled()) logger.debug("注入用户类型名称集合...");
		this.typeNameMap = typeNameMap;
	}
	/**
	 * 设置状态名称集合。
	 * @param statusNameMap
	 * 状态名称集合。
	 */
	public void setStatusNameMap(Map<Integer, String> statusNameMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态名称集合...");
		this.statusNameMap = statusNameMap;
	}
	/*
	 * 加载性别名称。
	 * @see com.softvan.netplatform.service.admin.IUserService#loadGenderName(java.lang.Integer)
	 */
	@Override
	public String loadGenderName(Integer gender) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载性别［%d］名称...", gender));
		if(this.genderNameMap == null || this.genderNameMap.size() == 0) return null;
		return this.genderNameMap.get(gender);
	}
	/*
	 * 加载用户类型名称。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#loadUserTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(logger.isDebugEnabled()) logger.debug(String.format("用户类型［%d］名称...", type));
		if(this.typeNameMap == null || this.typeNameMap.size() == 0) return null;
		return this.typeNameMap.get(type);
	}
	/*
	 * 加载用户状态名称。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户状态［%d］名称...", status));
		if(this.statusNameMap == null || this.statusNameMap.size() == 0) return null;
		return this.statusNameMap.get(status);
	}
	/*
	 * 查找数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<User> find(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		 return this.userDao.findUsers(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected UserInfo changeModel(User data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 User => UserInfo ...");
		return this.changeModel(data, false);
	}
	//数据模型转换。
	private UserInfo changeModel(User data, boolean isViewPwd){
		if(data == null) return null;
		UserInfo info = new UserInfo(); 
		BeanUtils.copyProperties(data, info, new String[]{"password"});
		//解密密码。
		/*if(isViewPwd){
			info.setPassword(this.passwordHelper.decryptAESPassword(data));
		}*/
		//性别
		if(info.getGender() != null){
			info.setGenderName(this.loadGenderName(info.getGender()));
		}
		//用户类型
		if(info.getType() != null){
			info.setTypeName(this.loadTypeName(info.getType()));
		}
		//状态
		if(info.getStatus() != null){
			info.setStatusName(this.loadStatusName(info.getStatus()));
		}
		//角色
		if(data.getRoles() != null){
			List<Long> listRoleId = new ArrayList<Long>();
			List<String>		listRoleName = new ArrayList<String>();
			for(Role role : data.getRoles()){
				if(role == null) continue;
				listRoleId.add(role.getId());
				listRoleName.add(role.getName());
			}
			info.setRoleId(listRoleId.toArray(new Long[0]));
			info.setRoleName(listRoleName.toArray(new String[0]));
		}
		return info;
	}
	/*
	 * 数据模型转换。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#conversion(com.softvan.netplatform.domain.admin.security.User, boolean)
	 */
	@Override
	public UserInfo conversion(User data, boolean isViewPwd) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 User => UserInfo...");
		return this.changeModel(data, isViewPwd);
	}
	/*
	 * 查询数据统计。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.userDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public UserInfo update(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		return this.changeModel(this.updateUser(info));
	}
	/*
	 *更新数据。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#updateUser(com.softvan.netplatform.model.admin.security.UserInfo)
	 */
	@Override
	public User updateUser(final UserInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		User user = StringUtils.isEmpty(info.getId()) ? null : this.userDao.load(User.class, info.getId());
		if(isAdded = (user == null)){
			//if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
		    long total =	this.total(new UserInfo(){
				private static final long serialVersionUID = 1L;
				@Override
				public String getAccount() { return info.getAccount(); }
			});
			if(total > 0) throw new RuntimeException(String.format("账号［%s］已存在！", info.getAccount()));
			user = new User();
		}else{
			info.setAccount(user.getAccount());
			info.setCreateTime(user.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		if(info.getType() == null){
			info.setType(UserType.BACKGROUND.getValue());
		}
		BeanUtils.copyProperties(info, user, new String[]{"password"});
		Set<Role> roles = null;
		if(info.getRoleId() != null && info.getRoleId().length > 0){
			roles = new HashSet<Role>();
			for(Long roleId : info.getRoleId()){
				if(StringUtils.isEmpty(roleId)) continue;
				Role role = this.roleDao.load(Role.class, roleId);
				if(role != null)roles.add(role);
			}
		}
		user.setRoles(roles);
		if(!StringUtils.isEmpty(info.getPassword())){
			//user.setPassword(this.passwordHelper.encryptAESPassword(info));
			user.setPassword(info.getPassword());//明文存入
		}
		if(isAdded){
			this.userDao.save(user);
		}else {
			this.userCache.removeUserCache(user.getAccount());//清除用户缓存。
		}
		return user;
	}
	/*
	 * 删除数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		 if(ids == null || ids.length == 0) return;
		 for(int i = 0; i  < ids.length; i++){
			 if(StringUtils.isEmpty(ids[i])) continue;
			 if(logger.isDebugEnabled()) logger.debug("删除数据：" + ids[i]);
			 this.deleteUser(ids[i], true);
		 }
	}
	//删除数据。
	private void deleteUser(String userId,boolean throwsException){
		if(StringUtils.isEmpty(userId)) return;
		 User data = this.userDao.load(User.class, userId);
		 if(data != null){
			/* if(!throwsException && (data.getAgencies() != null && data.getAgencies().size() > 0)){
				 return;
			 }*/
			 this.userDao.delete(data);
			 this.userCache.removeUserCache(data.getAccount());//清除用户缓存。
		 }
	}
	/*
	 * 删除数据。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除用户［%s］....", userId));
		this.deleteUser(userId, false);
	}
	/*
	 * 修改密码。
	 * @see com.softvan.netplatform.service.admin.security.IUserService#modifyPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void modifyPassword(Long userId,String oldPassword,String newPassword) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新用户［%1$s］密码:［%1$s］ =>［％2$s］", userId,oldPassword,newPassword));
		if(StringUtils.isEmpty(userId)) throw new Exception("用户ID为空！");
		if(StringUtils.isEmpty(newPassword)) throw new Exception("新密码为空！");
		User user = this.userDao.load(User.class, userId);
		if(user == null) throw new Exception(String.format("用户［％s］不存在！", userId));
		if(!StringUtils.isEmpty(oldPassword)){//验证旧密码。
			/*String old_pwd = this.passwordHelper.decryptAESPassword(user);*/
			
			if(!oldPassword.equalsIgnoreCase(user.getPassword())) throw new Exception("旧密码错误！");
		}
		//UserInfo info = new UserInfo();
		//BeanUtils.copyProperties(user, info, new String[]{"password"});
		//info.setPassword(newPassword);
		/*	user.setPassword(this.passwordHelper.encryptAESPassword(info));*/
		user.setPassword(newPassword);
		if(logger.isDebugEnabled()) logger.debug("密码修改成功！");
		this.userCache.removeUserCache(user.getAccount());//清除用户缓存。
	}
	/*
	 * 根据账号加载用户。
	 * @see com.softvan.netplatform.service.admin.security.IUserAuthorization#loadUserByAccount(java.lang.String)
	 */
	@Override
	public User loadUserByAccount(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号［%s］加载用户...", account));
		if(StringUtils.isEmpty(account)) return null;
		return this.userDao.findByAccount(account);
	}
	/*
	 * 根据账号查找用户角色ID集合。
	 * @see com.softvan.netplatform.service.admin.IUserService#findRoles(java.lang.String)
	 */
	@Override
	public Set<String> findRolesByAccount(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号［%s］查找用户角色ID集合...", account));
		Set<String> roleCodes = new HashSet<String>();
		User user = this.loadUserByAccount(account);
		if(user != null && user.getRoles() != null && user.getRoles().size() > 0){
			 for(Role role : user.getRoles()){
				 if(role == null || role.getStatus() == null || role.getStatus() != Status.ENABLED.getValue()) continue;
				 roleCodes.add(role.getCode());
			 }
		}
		return roleCodes;
	}
	/*
	 * 查询权限集合。
	 * @see com.softvan.wechat.service.security.IUserService#findPermissions(java.lang.String)
	 */
	@Override
	public Set<String> findPermissionsByAccount(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询账号［%s］权限集合...", account));
		Set<String> rightCodes = new HashSet<String>();
		User user =	this.loadUserByAccount(account);
		if(user != null && user.getRoles() != null && user.getRoles().size() > 0){
			 for(Role role : user.getRoles()){
				 if(role == null || role.getStatus() == null || role.getStatus() != Status.ENABLED.getValue() || role.getRights() == null || role.getRights().size() == 0) continue;
				 for(MenuRight menuRight : role.getRights()){
					 String code = null;
					 if(menuRight == null || StringUtils.isEmpty(code = menuRight.getCode())) continue;
					 if(!rightCodes.contains(code)){
						 rightCodes.add(code);
					 }
				 }
			 }
		}
		return rightCodes;
	}
	/*
	 * 初始化用户。
	 * @see com.softvan.wechat.service.security.IUserService#init(java.lang.String)
	 */
	@Override
	public void init(Long roleId,String account, String password) throws Exception {
		if(logger.isDebugEnabled())logger.debug(String.format("初始化用户［roleId = %1$s,account=%2$s,password=%3$s］...",roleId,account,password));
		if(StringUtils.isEmpty(roleId)) throw new Exception("角色ID为空！");
		if(StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) throw new Exception("账号或密码为空！");
		UserInfo info = new UserInfo();
		info.setAccount(account);
		info.setName(account);
		info.setNickName(account);
		info.setPassword(password);
		info.setRoleId(new Long[]{ roleId });
		info.setGender(Gender.NONE.getValue());
		info.setStatus(Status.ENABLED.getValue());
		this.updateUser(info);
		if(logger.isDebugEnabled()) logger.debug("初始化用户完成。");
		this.userCache.removeAuthorizationCache();
	}
	@Override
	public User findById(Long id) {
		return this.userDao.findById(id);
	}

	@Override
	public User findByUserCode(String usercode) {
		Criteria  c = userDao.getSession().createCriteria(User.class);
		c.add(Restrictions.eq("account", usercode));
		List<User> list = c.list();
		return list == null || list.size() == 0 ? null : list.get(0);
	}
}