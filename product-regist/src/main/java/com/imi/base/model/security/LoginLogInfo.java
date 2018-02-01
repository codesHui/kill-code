package com.imi.base.model.security;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.softvan.model.Paging;
import com.softvan.support.CustomDateSerializer;
/**
 * 登录日志信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginLogInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private long id;
	private String account,ip,browser;
	private Date time;
	/**
	 * 构造函数。
	 */
	public LoginLogInfo(){
		this.setTime(new Date());
	}
	/**
	 * 构造函数。
	 * @param account
	 * 账号。
	 * @param ip
	 * IP地址。
	 * @param browser
	 * 浏览器信息。
	 */
	public LoginLogInfo(String account,String ip,String browser){
		this();
		this.setAccount(account);
		this.setIp(ip);
		this.setBrowser(browser);
	}
	
	/**
	 * 获取登录账号。
	 * @return
	 * 登录账号。
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置登录账号。
	 * @param account
	 * 登录账号。
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取登录IP。
	 * @return
	 * 登录IP。
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置登录IP。
	 * @param ip
	 * 登录IP。
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取浏览器版本。
	 * @return
	 * 浏览器版本。
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * 设置浏览器版本。
	 * @param browser
	 * 浏览器版本。
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * 获取登录时间。
	 * @return
	 * 登录时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getTime() {
		return time;
	}
	/**
	 * 设置登录时间。
	 * @param time
	 * 登录时间。
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}