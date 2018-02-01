package com.softvan.configuration;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 配置系统。
 * 
 */
@XStreamAlias("ipower")
public class IPowerSystem implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAsAttribute
	private String version;
	@XStreamImplicit
	private ModuleSystemCollection systems;
	/**
	 * 构造函数。
	 */
	public IPowerSystem(){
		this.setSystems(new ModuleSystemCollection());
	}
	/**
	 * 构造函数。
	 * @param version
	 * 版本。
	 */
	public IPowerSystem(String version){
		this();
		this.setVersion(version);
	}
	/**
	 * 获取版本。
	 * @return 版本。
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置版本。
	 * @param version 
	 *	  版本。
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取模块系统集合。
	 * @return 模块系统集合。
	 */
	public ModuleSystemCollection getSystems() {
		return systems;
	}
	/**
	 * 设置模块系统集合。
	 * @param systems 
	 *	  模块系统集合。
	 */
	public void setSystems(ModuleSystemCollection systems) {
		this.systems = systems;
	}
	/**
	 * 添加模块系统。
	 * @param moduleSystem
	 * 模块系统。
	 */
	public void add(ModuleSystem moduleSystem){
		if(moduleSystem != null && !StringUtils.isEmpty(moduleSystem.getId())){
			this.systems.add(moduleSystem);
		}
	}
}