package com.softvan.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 分页对象。
 * */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Paging implements IPaging {
	private static final long serialVersionUID = 1L;
	private String sort, order;
	private Integer rows, page;
	/**
	 * 获取每页数据量。
	 * @return 每页数据量。
	 * */
	@Override
	public Integer getRows() {
		return this.rows;
	}
	/**
	 * 设置每页数据量。
	 * @param rows
	 * 	每页数据量。
	 * */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/**
	 * 获取页码。
	 * @return 页码。
	 * */
	@Override
	public Integer getPage() {
		return this.page;
	}
	/**
	 * 设置页码。
	 * @param page
	 * 	页码。
	 * */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	/**
	 * 获取排序字段名称。
	 * @return 排序字段名称。
	 * */
	@Override
	public String getSort() {
		return this.sort;
	}
	/**
	 * 设置排序字段名称。
	 * @param sort
	 * 	排序字段名称。
	 * */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 获取排序方式。
	 * @return 排序方式。
	 * */
	@Override
	public String getOrder() {
		return this.order;
	}
	/**
	 * 设置排序方式。
	 * @param order
	 *  排序方式。
	 * */
	@Override
	public void setOrder(String order) {
		 this.order = order;
	}
}