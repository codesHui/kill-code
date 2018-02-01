package com.imi.service.security.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import com.imi.base.model.security.LoginLogInfo;
import com.imi.service.security.ILoginLogService;
import com.imi.service.security.IUserAuthentication;
import com.imi.service.security.VerifyCodeType;
import com.softvan.aware.IUserAware;
import com.softvan.utils.VerifyCodeUtil;

/**
 * 用户认证服务接口实现类。

 */
public class UserAuthenticationImpl implements IUserAuthentication, IUserAware {
	private static final Logger logger = Logger.getLogger(UserAuthenticationImpl.class);
	private static final int default_verify_code_length = 4, default_verify_code_img_width = 90, default_verify_code_img_height = 30, default_verify_code_img_inter_line = 3;
	private Integer verifyCodeType, verifyCodeLength, verifyCodeImageHeight,verifyCodeImageWidth, verifyCodeImageInterLine;
	private Long userId;
	private String userName,userNickName,roleCode;
	private ILoginLogService loginLogService;
	/**
	 * 设置验证码类型。
	 * @param verifyCodeType 
	 *	  验证码类型。
	 */
	public void setVerifyCodeType(Integer verifyCodeType) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入验证码类型[%d]...", verifyCodeType));
		this.verifyCodeType = verifyCodeType;
	}
	/**
	 * 设置验证码长度。
	 * @param verifyCodeLength 
	 *	  验证码长度。
	 */
	public void setVerifyCodeLength(Integer verifyCodeLength) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入验证码长度[%d]...", verifyCodeLength));
		this.verifyCodeLength = verifyCodeLength;
	}
	/**
	 * 设置验证码图片高度。
	 * @param verifyCodeImageHeight 
	 *	  验证码图片高度。
	 */
	public void setVerifyCodeImageHeight(Integer verifyCodeImageHeight) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入验证码图片高度［%d］...", verifyCodeImageHeight));
		this.verifyCodeImageHeight = verifyCodeImageHeight;
	}

	/**
	 * 设置验证码图片宽度。
	 * @param verifyCodeImageWidth 
	 *	  验证码图片宽度。
	 */
	public void setVerifyCodeImageWidth(Integer verifyCodeImageWidth) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入验证码图片宽度［%d］...", verifyCodeImageWidth));
		this.verifyCodeImageWidth = verifyCodeImageWidth;
	}
	/**
	 * 设置验证码图片干扰线条数。
	 * @param verifyCodeImageInterLine 
	 *	  验证码图片干扰线条数。
	 */
	public void setVerifyCodeImageInterLine(Integer verifyCodeImageInterLine) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入验证码图片干扰线条数［%d］...", verifyCodeImageInterLine));
		this.verifyCodeImageInterLine = verifyCodeImageInterLine;
	}

	/**
	 * 设置登录日志服务接口。
	 * @param loginLogService 
	 *	  登录日志服务接口。
	 */
	public void setLoginLogService(ILoginLogService loginLogService) {
		if(logger.isDebugEnabled()) logger.debug("注入登录日志服务接口...");
		this.loginLogService = loginLogService;
	}
	/*
	 * 设置用户ID。
	 * @see com.softvan.aware.IUserAware#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(Long userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入用户ID［%s］...", userId));
		this.userId = userId;
	}
	/*
	 * 设置用户账号。
	 * @see com.softvan.aware.IUserAware#setUserName(java.lang.String)
	 */
	@Override
	public void setUserName(String userName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入用户账号［%s］...", userName));
		this.userName = userName;
	}
	/*
	 * 设置用户昵称。
	 * @see com.softvan.aware.IUserAware#setUserNickName(java.lang.String)
	 */
	@Override
	public void setUserNickName(String userNickName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入用户昵称［%s］...", userNickName));
		this.userNickName = userNickName;
	}
	
	/**
	 * 获取当前用户RoleCode
	 */
	@Override
	public void setRoleCode(String roleCode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入用户角色［%s］...", roleCode));

		this.roleCode=roleCode;
		
	}
	/*
	 * 创建验证码。
	 * @see com.softvan.netplatform.service.admin.security.IUserAuthentication#createVerifyCode()
	 */
	@Override
	public String createVerifyCode() {
		if(logger.isDebugEnabled()) logger.debug("创建验证码...");
		VerifyCodeType type = VerifyCodeType.conversion(this.verifyCodeType);
		int len = this.verifyCodeLength == null ? default_verify_code_length : this.verifyCodeLength;
		return VerifyCodeUtil.generateTextCode(type.getValue(),len, null);
	}
	/*
	 * 加载验证码图片。
	 * @see com.softvan.netplatform.service.admin.security.IUserAuthentication#loadVerifyCodeImage(java.lang.String)
	 */
	@Override
	public BufferedImage loadVerifyCodeImage(String verifyCode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载验证码［%s］图片...", verifyCode));
		if(StringUtils.isEmpty(verifyCode)) throw new RuntimeException("验证码不存在！");
		int w = (this.verifyCodeImageWidth == null) ? default_verify_code_img_width : this.verifyCodeImageWidth,
			 h = (this.verifyCodeImageHeight == null) ? default_verify_code_img_height : this.verifyCodeImageHeight,
		    lines = (this.verifyCodeImageInterLine == null) ? default_verify_code_img_inter_line : this.verifyCodeImageInterLine;
		
		return VerifyCodeUtil.generateImageCode(verifyCode, w, h, lines, true, Color.WHITE, Color.RED, null);
	}
	/*
	 * 验证用户。
	 * @see com.softvan.netplatform.service.admin.security.IUserAuthentication#authentication(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void authentication(String account, String password,String reqIP, String reqBrowser) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("验证用户［account = %1$s］［password = %2$s］［reqIP = %3$s］［reqBrowser = %4$s］...", account, password, reqIP, reqBrowser));
		try{
			UsernamePasswordToken token = new UsernamePasswordToken(account, password);
			token.setRememberMe(false);
			//获取当前的Subject
			Subject subject = SecurityUtils.getSubject();
			if(logger.isDebugEnabled())logger.debug(String.format("对用户[%s]进行登录验证.....验证开始.", account));
			subject.login(token);
			if(logger.isDebugEnabled())logger.debug(String.format("对用户[%s]进行登录验证.....验证通过.", account));
			//验证通过写入日志；
			this.loginLogService.update(new LoginLogInfo(account, reqIP, reqBrowser));
		}catch(UnknownAccountException e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证未通过:未知账户[%s]", e.getMessage()), e);
			throw new UnknownAccountException("未知账户", e);
		}catch(IncorrectCredentialsException e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证未通过:密码不正确[%s]", e.getMessage()), e);
			throw new IncorrectCredentialsException("密码不正确", e);
		}catch(LockedAccountException e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证未通过:账户已锁定[%s]", e.getMessage()), e);
			throw new LockedAccountException("账户已锁定", e);
		}catch(ExcessiveAttemptsException e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证未通过:用户名或密码错误次数过多[%s]", e.getMessage()), e);
			throw new ExcessiveAttemptsException("用户名或密码错误次数过多", e);
		}catch(AuthenticationException e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证未通过:用户名或密码不正确[%s]",e.getMessage()), e);
			throw new AuthenticationException("用户名或密码不正确", e);
		}catch(Exception e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证未通过:未知错误[%s]",e.getMessage()), e);
			throw new Exception("未知错误", e);
		}
	}
	/*
	 * 用户注销。
	 * @see com.softvan.netplatform.service.admin.security.IUserAuthentication#logout()
	 */
	@Override
	public void logout() {
		if(logger.isDebugEnabled()) logger.debug(String.format("用户［userId = %1$s ,userName = %2$s ,userNickName = %3$s］注销...", this.userId, this.userName,this.userNickName));
		SecurityUtils.getSubject().logout();
	}

	
}