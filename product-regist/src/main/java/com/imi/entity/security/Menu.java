package com.imi.entity.security;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.imi.base.entity.AbstractEntity;
import com.imi.base.entity.Orderable;

/**
 * 菜单数据
 */
@Entity
@Table(name = "sys_menus")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region = "security")
public class Menu extends AbstractEntity implements Orderable {
	private static final long serialVersionUID = 1L;
	private String icon, name, uri;
	private int orderNo;
	  
	   

	private Menu parent;
	
  

	private Set<Menu> children;
	

	private Set<MenuRight> rights;

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public String getUri() {
		return uri;
	}

	public int getOrderNo() {
		return orderNo;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_id")
    @NotFound(action = NotFoundAction.IGNORE)
	public Menu getParent() {
		return parent;
	}
	@OneToMany(mappedBy = "parent")
	@Cascade(value = {CascadeType.SAVE_UPDATE})
	@OrderBy(value="orderNo asc")
	public Set<Menu> getChildren() {
		return children;
	}
	@OneToMany
	@JoinColumn(name = "menu_id")
	@OrderBy(value="code asc")
	public Set<MenuRight> getRights() {
		return rights;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

	public void setRights(Set<MenuRight> rights) {
		this.rights = rights;
	}

	
	

	@Override
	public int compareTo(Orderable o) {
		return (int) (getOrderNo() - o.getOrderNo());
	}

}