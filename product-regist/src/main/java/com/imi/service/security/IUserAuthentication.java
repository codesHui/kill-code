package com.imi.service.security;

import java.awt.image.BufferedImage;

/**
 * 用户认证服务接口。
 * 
 * @author josh
 * @since 2014年10月28日
 */
public interface IUserAuthentication {
	/**
	 * 创建验证码。
	 * @return 验证码。
	 */
	String createVerifyCode();
	/**
	 * 加载验证码图片对象。
	 * @param verifyCode
	 * 验证码。
	 * @return
	 * 验证码图片对象。
	 */
	BufferedImage loadVerifyCodeImage(String verifyCode); 
	/**
	 * 验证用户。
	 * @param account
	 * 用户账号。
	 * @param password
	 * 用户密码。
	 * @param reqIP
	 * 请求IP地址。
	 * @param reqBrowser
	 * 请求浏览器。
	 * @return
	 * @throws Exception
	 */
	void authentication(String account,String password,String reqIP, String reqBrowser) throws Exception;
	/**
	 * 用户卸载。
	 */
	void logout();
}