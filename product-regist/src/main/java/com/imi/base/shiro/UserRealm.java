package com.imi.base.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.StringUtils;

import com.imi.entity.security.User;
import com.imi.service.security.IUserAuthorization;
import com.imi.support.PasswordHelper;
import com.softvan.service.Status;

/**
 * 用户认证。
 */
public class UserRealm extends AuthorizingRealm implements IUserCache {
	private static final Logger logger = Logger.getLogger(UserRealm.class);
	private IUserAuthorization userAuthorization;
	private PasswordHelper passwordHelper;
	/**
	 * 设置用户授权服务接口。
	 * @param userAuthorization
	 * 用户授权服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		if(logger.isDebugEnabled()) logger.debug("注入用户授权服务接口...");
		this.userAuthorization = userAuthorization;
	}
	/**
	 * 设置密码工具。
	 * @param passwordHelper
	 * 密码工具。
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		if(logger.isDebugEnabled()) logger.debug("注入密码工具...");
		this.passwordHelper = passwordHelper;
	}
	/*
	 * 执行获取授权信息。
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(logger.isDebugEnabled()) logger.debug("执行获取授权信息...");
		String account = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(this.userAuthorization.findRolesByAccount(account));
		//authorizationInfo.setStringPermissions(this.userAuthorization.findPermissionsByAccount(account));
		System.out.println(authorizationInfo);
		return authorizationInfo;
	}
	/*
	 * 执行获得认证信息。
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(logger.isDebugEnabled()) logger.debug("执行获得认证信息...");
		String account = (String)token.getPrincipal();
		User user = this.userAuthorization.loadUserByAccount(account);
		if(user == null) throw new UnknownAccountException();//没找到账号。
		if(user.getStatus() == Status.DISABLE.getValue()){
			throw new LockedAccountException();//账号锁定。
		}
		String pwd = this.passwordHelper.encryptPassword(user);
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配。
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
					user.getAccount(),//账号
					pwd,
					ByteSource.Util.bytes(user.getId().toString()),
					this.getName()
				);
		
		return authenticationInfo;
	}
    /*
     * 移除用户缓存。
     * @see com.imi.base.shiro.IShiroUserCache#removeUserCache(java.lang.String)
     */
	@Override
	public void removeUserCache(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("移除用户［account ＝ %s］缓存...", account));
		if(StringUtils.isEmpty(account)) return;
		SimplePrincipalCollection pc = new SimplePrincipalCollection();
		pc.add(account, this.getName());
		this.clearCachedAuthorizationInfo(pc);
		this.clearCachedAuthenticationInfo(pc);
	}
	/*
	 * 移除全部授权缓存。
	 * @see com.imi.base.shiro.IShiroUserCache#removeAuthorizationCache()
	 */
	@Override
	public void removeAuthorizationCache() {
		if(logger.isDebugEnabled()) logger.debug("移除全部授权缓存...");
		this.getAuthorizationCache().clear();
	}
}