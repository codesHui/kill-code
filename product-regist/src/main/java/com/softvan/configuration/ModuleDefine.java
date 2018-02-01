package com.softvan.configuration;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
/**
 * 模块系统信息。
 * */
@XStreamAlias("module")
public class ModuleDefine implements Serializable,Comparable<ModuleDefine> {
	private static final long serialVersionUID = 1L;	
	@XStreamAsAttribute
	private String id,icon, name, uri;
	@XStreamAsAttribute
	private Integer order = 0;
	@XStreamImplicit
	private ModuleDefineCollection modules;
	/**
	 * 构造函数。
	 * */
	public ModuleDefine(){
		this.setModules(new ModuleDefineCollection());
	}
	/**
	 * 构造函数。
	 * @param id
	 * 模块ID。
	 * @param icon
	 * 图标。
	 * @param name
	 * 模块名称。
	 * @param uri
	 * 模块URI。
	 * @param order
	 * 排序号。
	 */
	public ModuleDefine(String id,String icon,String name,String uri, Integer order){
		this();
		this.setId(id);
		this.setIcon(icon);
		this.setName(name);
		this.setUri(uri);
		this.setOrder(order);
	}
	/**
	 * 获取模块ID。
	 * @return 模块ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置模块ID。
	 * @param id
	 * 	模块ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取图标。
	 * @return 图标。
	 * */
	public String getIcon() {
		return icon;
	}
	/**
	 *  设置图标
	 * @param icon
	 * 图标。
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * 获取模块名称。
	 * @return 模块名称。
	 * */
	public String getName() {
		return name;
	}
	/**
	 * 设置模块名称。
	 * @param name
	 * 	模块名称。
	 * */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取模块URI。
	 * @return 模块URI。
	 * */
	public String getUri() {
		return uri;
	}
	/**
	 * 设置模块URI。
	 * @param uri
	 * 	模块URI。
	 * */
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 * */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置排序号。
	 * @param order
	 * 	排序号。
	 * */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取子模块集合。
	 * @return 子模块集合。
	 * */
	public ModuleDefineCollection getModules() {
		return modules;
	}
	/**
	 * 设置子模块集合。
	 * @param modules
	 * 	子模块集合。
	 * */
	public void setModules(ModuleDefineCollection modules) {
		this.modules = modules;
	}
	/**
	 * 添加子模块。
	 * @param moduleDefine
	 * 子模块。
	 */
	public void addChild(ModuleDefine moduleDefine){
		if(moduleDefine == null || this.modules == null) return;
		this.modules.add(moduleDefine);
	}
	/*
	 * 排序比较。
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ModuleDefine o) {
		int index = 0;
		if(this == o) return index;
		index = this.getOrder() - o.getOrder();
		if(index == 0){
			index = this.getName().compareToIgnoreCase(o.getName());
			if(index == 0){
				index = this.getId().compareToIgnoreCase(o.getId());
			}
		}
		return index;
	}
}