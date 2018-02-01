package com.softvan.shiro.freemarker;
/**
 * 检查是否有权限。
 */
public class HasPermissionTag extends PermissionTag {
	/*
	 * 当前用户是否有权限。
	 * @see com.softvan.shiro.freemarker.PermissionTag#showTagBody(java.lang.String)
	 */
	@Override
	protected boolean showTagBody(String p) {
		return this.isPermitted(p);
	}

}