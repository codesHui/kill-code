package com.imi.base.model.security;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.softvan.model.Paging;
/**
 * 菜单权限信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MenuRightInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private Long id,rightId,menuId;
	private String code,menuName,rightName;
	/**
	 *  构造函数。
	 */
	public MenuRightInfo(){
	}
	/**
	 * 构造函数。
	 * @param menuId
	 * 菜单ID。
	 * @param rightId
	 * 权限ID。
	 */
	public MenuRightInfo(Long menuId,Long rightId){
		
		this.setMenuId(menuId);
		this.setRightId(rightId);
	}
	public Long getId() {
		return id;
	}
	public Long getRightId() {
		return rightId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public String getCode() {
		return code;
	}
	public String getMenuName() {
		return menuName;
	}
	public String getRightName() {
		return rightName;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	
	
	
	
}