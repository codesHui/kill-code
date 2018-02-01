package com.imi.entity.security;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.entity.AbstractEntity;
import com.imi.base.entity.Orderable;
/**
 * 基础权限。
 */
@Entity
@Table(name = "sys_rights")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "security")
public class Right  extends AbstractEntity implements Orderable{
	private static final long serialVersionUID = 1L;
	private String name;
	private int value,orderNo;
	
	
	private Set<MenuRight> menus;
	/**
	 *  查看数据权限。
	 */
	public static final int VIEW = 1;
	/**
	 * 更新数据权限。
	 */
	public static final int UPDATE = 2;
	/**
	 * 删除数据权限。
	 */
	public static final int DELETE = 3;
	
	public String getName() {
		return name;
	}
	public int getValue() {
		return value;
	}
	public int getOrderNo() {
		return orderNo;
	}
	@OneToMany
	@JoinColumn(name = "right_id")
	public Set<MenuRight> getMenus() {
		return menus;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public void setMenus(Set<MenuRight> menus) {
		this.menus = menus;
	}
	
	
	@Override
	public int compareTo(Orderable o) {
		return (int) (getOrderNo() - o.getOrderNo());
	}
	
	
	
}