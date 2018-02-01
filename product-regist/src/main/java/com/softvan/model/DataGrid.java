package com.softvan.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * easyui的datagrid数据模型。
 * */
@JsonSerialize(include = Inclusion.NON_NULL)
public class DataGrid<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long total = 0L;
	private List<T> rows;
	/**
	 * 获取总记录数。
	 * @return 总记录数。
	 * */
	public Long getTotal() {
		return total;
	}
	/**
	 * 设置总记录数。
	 * @param total
	 * 	总记录数。
	 * */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * 获取数据集合。
	 * @return 数据集合。
	 * */
	public List<T> getRows() {
		return rows;
	}
	/**
	 * 设置数据集合
	 * @param rows
	 * 	数据集合。
	 * */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}