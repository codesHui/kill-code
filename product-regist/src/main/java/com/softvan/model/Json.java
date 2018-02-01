package com.softvan.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.imi.entity.setting.CommonMessage;

/**
 * JSON数据模型。
 * @author young。
 * 
 * */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Json implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean success = false;
	private Object data;
	private String msg;
	private String id;
	private String code;
	//站内信 未读
	private int readNo;
	// 站内信  总数
	private int readTotal;
	private List<CommonMessage> list ;
	
	
	public List<CommonMessage> getList() {
		return list;
	}
	public void setList(List<CommonMessage> list) {
		this.list = list;
	}
	public int getReadNo() {
		return readNo;
	}
	public void setReadNo(int readNo) {
		this.readNo = readNo;
	}
	public int getReadTotal() {
		return readTotal;
	}
	public void setReadTotal(int readTotal) {
		this.readTotal = readTotal;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 构造函数。
	 */
	public Json(){
		this.setSuccess(false);
	}
	/**
	 * 获取是否成功。
	 * @return 是否成功。
	 * */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 设置是否成功。
	 * @param success
	 * 	是否成功。
	 * */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 获取数据。
	 * @return 数据。
	 * */
	public Object getData() {
		return data;
	}
	/**
	 * 设置数据。
	 * @param data
	 * 	数据。
	 * */
	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * 获取提示信息。
	 * @return 提示信息。
	 * */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置提示信息。
	 * @param msg
	 * 	提示信息。
	 * */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}