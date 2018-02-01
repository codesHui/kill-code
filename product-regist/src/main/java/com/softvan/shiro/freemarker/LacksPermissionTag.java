package com.softvan.shiro.freemarker;
/**
 * 权限反选。
 * @author josh.
 * @since 2014-05-16.
 */
public class LacksPermissionTag extends PermissionTag {
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.PermissionTag#showTagBody(java.lang.String)
	 */
	@Override
	protected boolean showTagBody(String p) {
		return !this.isPermitted(p);
	}
}