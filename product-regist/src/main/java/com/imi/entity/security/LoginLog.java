package com.imi.entity.security;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.imi.base.BaseEntity;
/**
 * 登录日志。
 */
@Entity
@Table(name = "sys_login_log")
@AssociationOverrides({
    @AssociationOverride(name = "creator", joinColumns = @JoinColumn(name = "create_id", updatable = false))
})
public class LoginLog extends BaseEntity{
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String account,ip,browser;

	public String getAccount() {
		return account;
	}
	public String getIp() {
		return ip;
	}
	public String getBrowser() {
		return browser;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	

}