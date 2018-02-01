package com.imi.entity.security;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.imi.base.entity.AbstractEntity;

/**
 * 菜单权限。
 */
@Entity
@Table(name = "sys_menu_rights")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "security")
public class MenuRight extends AbstractEntity  {
	private static final long serialVersionUID = 1L;

	private String code;
	
	private Menu menu;
	private Right right;
	private Set<Role> roles;

	public MenuRight(Menu menu, Right right) {
		
	}
	public MenuRight() {
		// TODO Auto-generated constructor stub
	}
	@ManyToOne
	@JoinColumn(name="menu_id")
	public Menu getMenu() {
		return menu;
	}
	@ManyToOne
	@JoinColumn(name="right_id")
	public Right getRight() {
		return right;
	}
	@ManyToMany
	@JoinTable(name = "sys_role_right", 
	joinColumns = @JoinColumn(name = "menuRight_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@Cascade(value = {CascadeType.SAVE_UPDATE}) 
	public Set<Role> getRoles() {
		return roles;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public void setRight(Right right) {
		this.right = right;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}