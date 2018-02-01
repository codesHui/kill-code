package com.imi.base.model.security;

import java.io.Serializable;
/**
 * 菜单权限提交信息。
 */
public class MenuRightPost implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long[] menuId,rightId;
	/**
	 * 获取菜单ID数组。
	 * @return 菜单ID数组。
	 */
	public Long[] getMenuId() {
		return menuId;
	}
	/**
	 * 设置菜单ID数组。
	 * @param menuId 
	 *	  菜单ID数组。
	 */
	public void setMenuId(Long[] menuId) {
		this.menuId = menuId;
	}
	/**
	 * 获取权限ID数组。
	 * @return 权限ID数组。
	 */
	public Long[] getRightId() {
		return rightId;
	}
	/**
	 * 设置权限ID数组。
	 * @param rightId 
	 *	  权限ID数组。
	 */
	public void setRightId(Long[] rightId) {
		this.rightId = rightId;
	}
}