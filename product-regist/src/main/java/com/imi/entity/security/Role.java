package com.imi.entity.security;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.imi.base.entity.AbstractEntity;

/**
 * 角色。
 */
@Entity
@Table(name = "sys_roles")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "security")
public class Role extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String code,name, description;
	
	private Integer status;
	private Set<User> users;
	private Set<MenuRight> rights;

	@Column(length = 64, nullable = false, unique = true)
	public String getName() {
		return name;
	}

	@Column(length = 250)
	public String getDescription() {
		return description;
	}
	@Column(length = 20, nullable = false, unique = true)
	public String getCode() {
		return code;
	}
	public Integer getStatus() {
		return status;
	}

	@ManyToMany(mappedBy="roles")
	public Set<User> getUsers() {
		return users;
	}

	@ManyToMany(mappedBy="roles")
	@Cascade(value = {CascadeType.SAVE_UPDATE})
	public Set<MenuRight> getRights() {
		return rights;
	}



	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}


	

	public void setCode(String code) {
		this.code = code;
	}


	public void setRights(Set<MenuRight> rights) {
		this.rights = rights;
	}

	@Override
	public String toString() {
		return "Role [code=" + code + ", name=" + name + ", description=" + description + ", status=" + status
				+ ", users=" + users + ", rights=" + rights + "]";
	}


}