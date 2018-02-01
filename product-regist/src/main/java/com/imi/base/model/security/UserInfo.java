package com.imi.base.model.security;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.softvan.model.Paging;
import com.softvan.support.CustomDateSerializer;
/**
 * 用户信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class UserInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name,account,password,nickName,imgUrl,phone,qq,email,genderName,typeName,statusName;
	private Integer gender,type,status;
	private String[] roleName;
	private Long[] roleId;
	private Date createTime;
	
	/**
	 * 构造函数。
	 */
	public UserInfo(){
		this.setCreateTime(new Date());
	}
	/**
	 * 获取用户ID。
	 * @return 用户ID。
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置用户ID。
	 * @param id
	 * 用户ID。
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取用户姓名。
	 * @return 用户姓名。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置用户姓名。
	 * @param name
	 * 用户姓名。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取用户昵称。
	 * @return 用户昵称。
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * 设置用户昵称。
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 获取用户账号。
	 * @return  用户账号。
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置用户账号。
	 * @param account
	 * 用户账号。
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取密文密码。
	 * @return 密文密码。
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置密文密码。
	 * @param password 
	 *  密文密码。
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取性别。
	 * @return 性别(1-男，2-女)。
	 */
	public Integer getGender() {
		return gender;
	}
	/**
	 * 设置性别(1-男，2-女)。
	 * @param gender
	 * 性别(1-男，2-女)。
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	/**
	 * 获取性别名称。
	 * @return 性别名称。
	 */
	public String getGenderName() {
		return genderName;
	}
	/**
	 * 设置性别名称。
	 * @param genderName
	 * 性别名称。
	 */
	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
	/**
	 * 获取手机号码。
	 * @return 手机号码。
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置手机号码。
	 * @param phone
	 * 手机号码。
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取QQ。
	 * @return QQ。
	 */
	public String getQq() {
		return qq;
	}
	/**
	 * 设置QQ。
	 * @param qq
	 * QQ。
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}
	/**
	 * 获取Email。
	 * @return  Email。
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置Email。
	 * @param email
	 * Email。
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 设置用户头像图片URL。
	 * @return  用户图片URL。
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * 设置用户头像图片URL。
	 * @param imgUrl
	 * 用户图片URL。
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 获取用户类型。
	 * @return 用户类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置用户类型。
	 * @param type 
	 *	  用户类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取用户类型名称。
	 * @return 用户类型名称。
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置用户类型名称。
	 * @param typeName 
	 *	  用户类型名称。
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取用户状态。
	 * @return 用户状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置用户状态。
	 * @param status
	 * 用户状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取状态名称。
	 * @return 状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置状态名称。
	 * @param statusName
	 * 状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取角色ID。
	 * @return 角色ID。
	 */
	public Long[] getRoleId() {
		return roleId;
	}
	/**
	 * 设置角色ID。
	 * @param roleId
	 * 角色ID。
	 */
	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}
	/**
	 * 获取角色名称。
	 * @return 角色名称。
	 */
	public String[] getRoleName() {
		return roleName;
	}
	/**
	 * 设置角色名称。
	 * @param roleName 
	 *	  角色名称。
	 */
	public void setRoleName(String[] roleName) {
		this.roleName = roleName;
	}
	/**
	 * 获取用户创建时间。
	 * @return 用户创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置用户创建时间。
	 * @param createTime
	 * 用户创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}