package com.imi.base.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 附件信息。
 * 
 * @author josh
 * @since 2014年10月22日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AttachmentInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,contentType,path;
	private Long size;
	/**
	 * 获取附件ID。
	 * @return 附件ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置附件ID。
	 * @param id 
	 *	  附件ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取附件名称。
	 * @return 附件名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置附件名称。
	 * @param name 
	 *	  附件名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取附件大小。
	 * @return 附件大小。
	 */
	public Long getSize() {
		return size;
	}
	/**
	 * 设置附件大小。
	 * @param size 
	 *	  附件大小。
	 */
	public void setSize(Long size) {
		this.size = size;
	}
	/**
	 * 获取内容类型。
	 * @return 内容类型。
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * 设置内容类型。
	 * @param contentType 
	 *	  内容类型。
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * 获取附件路径。
	 * @return 附件路径。
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置附件路径。
	 * @param path 
	 *	  附件路径。
	 */
	public void setPath(String path) {
		this.path = path;
	}
}