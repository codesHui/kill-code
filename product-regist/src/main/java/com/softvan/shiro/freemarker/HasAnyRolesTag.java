package com.softvan.shiro.freemarker;

import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

/**
 * 是否有指定的角色。

 */
public class HasAnyRolesTag extends RoleTag {
	private static final String ROLE_NAMES_DELIMETER = ",";
	/*
	 * 检查角色是否存在。
	 * @see com.softvan.shiro.freemarker.RoleTag#showTagBody(java.lang.String)
	 */
	@Override
	protected boolean showTagBody(String roleNames) {
		if(!StringUtils.isEmpty(roleNames)){
			Subject subject = this.getSubject();
			if(subject != null){
				for(String role : roleNames.split(ROLE_NAMES_DELIMETER)){
					if(subject.hasRole(role.trim())){
						return true;
					}
				}
			}
		}
		return false;
	}

}