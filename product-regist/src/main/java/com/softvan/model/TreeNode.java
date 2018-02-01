package com.softvan.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * easyui使用的tree结构数据。
 * */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TreeNode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,text,iconCls,state = "open";
	private Map<String, Object> attributes;
	private List<TreeNode> children;
	/**
	 * 构造函数。
	 */
	public TreeNode(){}
	/**
	 * 构造函数。
	 * @param id
	 * @param text
	 */
	public TreeNode(String id,String text){
		this();
		this.setId(id);
		this.setText(text);
	}
	/**
	 * 获取节点ID。
	 * @return 节点ID。
	 * */
	public String getId() {
		return id;
	}
	/**
	 * 设置节点ID。
	 * @param id
	 * 	节点ID。
	 * */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取节点名称。
	 * @return 节点名称。
	 * */
	public String getText() {
		return text;
	}
	/**
	 * 设置节点名称。
	 * @param text
	 * 	节点名称。
	 * */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取节点图标样式。
	 * @return 节点图标样式。
	 * */
	public String getIconCls() {
		return iconCls;
	}
	/**
	 * 设置节点图标样式。
	 * @param 节点图标样式。
	 * */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	/**
	 * 获取节点状态。
	 * @return 状态。
	 * */
	public String getState() {
		return state;
	}
	/**
	 * 设置节点状态。
	 * @param state
	 * 	节点状态。
	 * */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * 获取其他参数集合。
	 * @return 其他参数集合。
	 * */
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	/**
	 * 设置其他参数集合。
	 * @param 其他参数集合。
	 * */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	/**
	 * 获取子节点集合。
	 * @return 子节点集合。
	 * */
	public List<TreeNode> getChildren() {
		return children;
	}
	/**
	 * 设置子节点集合。
	 * @param children
	 * 子节点集合。
	 * */
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
}