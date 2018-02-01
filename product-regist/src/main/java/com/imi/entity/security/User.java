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

import com.imi.base.BaseEntity;

/**
 * 用户。
 */
@Entity
@Table(name = "sys_users")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "sys_users")
public class User extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String name, account, password, nickName, imgUrl, phone, qq, email;
	private Integer gender, type, status;
	private Set<Role> roles;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Column(length=20,nullable=true )
	public String getName() {
		return name;
	}
	
			
	@Column(length=32,nullable=true,unique=true)		
	public String getAccount() {
		return account;
	}
	@Column(length=512,nullable=true)	
	public String getPassword() {
		return password;
	}
	@Column(length=512)	
	public String getNickName() {
		return nickName;
	}
	@Column(length=255)	
	public String getImgUrl() {
		return imgUrl;
	}
	@Column(length=20)	
	public String getPhone() {
		return phone;
	}
	@Column(length=20)
	public String getQq() {
		return qq;
	}
	@Column(length=200)
	public String getEmail() {
		return email;
	}

	public Integer getGender() {
		return gender;
	}

	public Integer getType() {
		return type;
	}

	public Integer getStatus() {
		return status;
	}
	
	@ManyToMany
	@Cascade(value = {CascadeType.SAVE_UPDATE}) 
	@JoinTable(name = "sys_user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

}