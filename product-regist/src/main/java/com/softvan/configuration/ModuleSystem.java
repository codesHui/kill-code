package com.softvan.configuration;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 模块系统信息。
 * */
@XStreamAlias("system")
public class ModuleSystem implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAsAttribute
	private String id;
	@XStreamAsAttribute
	private String sign;
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String description;
	@XStreamImplicit
	private ModuleDefineCollection modules;
	/**
	 * 构造函数。
	 * */
	public ModuleSystem(){
		this.setModules(new ModuleDefineCollection());
	}
	/**
	 * 构造函数。
	 * @param id
	 * 系统ID。
	 * @param sign
	 * 系统标示。
	 * @param name
	 * 系统名称。
	 * @param description
	 * 系统描述。
	 */
	public ModuleSystem(String id,String sign,String name,String description){
		this();
		this.setId(id);
		this.setSign(sign);
		this.setName(name);
		this.setDescription(description);
	}
	/**
	 * 获取系统ID。
	 * @return 系统ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置系统ID。
	 * @param id
	 * 	系统ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取系统标示。
	 * @return 系统标示。
	 * */
	public String getSign() {
		return sign;
	}
	/**
	 * 设置系统标示。
	 * @param sign
	 * 	系统标示。
	 * */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * 获取系统名称。
	 * @return 系统名称。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置系统名称。
	 * @param name
	 * 	系统名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取系统描述。
	 * @return 系统描述。
	 * */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置系统描述。
	 * @param description
	 * 	系统描述。
	 * */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取模块集合。
	 * @return 系统集合。
	 * */
	public ModuleDefineCollection getModules() {
		return modules;
	}
	/**
	 * 设置模块集合。
	 * @param 模块集合。
	 * */
	public void setModules(ModuleDefineCollection modules) {
		this.modules = modules;
	}
	/**
	 * 添加模块。
	 * @param moduleDefine
	 * 模块。
	 */
	public void add(ModuleDefine moduleDefine){
		if(moduleDefine == null || this.modules == null) return;
		this.modules.add(moduleDefine);
	}
	/*
	 * 对象字符串。
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("id=%1$s,sign=%2$s,name=%2$s,description=%3$s,modules={%4$s}", 
				this.getId(), this.getSign(), this.getName(), this.getDescription(), this.getModules());
	}
}