package com.softvan.shiro.freemarker;

import org.apache.shiro.subject.Subject;

/**
 * 角色反选。
 * @author josh.
 * @since 2014-05-16.
 */
public class LacksRoleTag extends RoleTag {
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.RoleTag#showTagBody(java.lang.String)
	 */
	@Override
	protected boolean showTagBody(String role) {
		Subject subject = this.getSubject();
		boolean hasRole = (subject != null && subject.hasRole(role));
		return !hasRole;
	}

}