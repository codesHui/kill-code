package com.softvan.shiro.freemarker;

import freemarker.template.SimpleHash;

/**
 * Shiro标签集合。
 * @author josh.
 * @since 2014-05-16.
 */
public class ShiroTags extends SimpleHash {
	private static final long serialVersionUID = 1L;
	/**
	 * 构造函数。
	 */
	public ShiroTags(){
		this.put("authenticated", new AuthenticatedTag());
		this.put("guest", new GuestTag());
		this.put("hasAnyRoles", new HasAnyRolesTag());
		this.put("hasPermission", new HasPermissionTag());
		this.put("hasRole", new HasRoleTag());
		this.put("lacksPermission", new LacksPermissionTag());
		this.put("lacksRole", new LacksRoleTag());
		this.put("notAuthenticated", new NotAuthenticatedTag());
		this.put("principal", new PrincipalTag());
		this.put("user", new UserTag());
	}
}