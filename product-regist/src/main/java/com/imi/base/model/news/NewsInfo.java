package com.imi.base.model.news;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.softvan.model.Paging;
/**
 * 用户信息。
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class NewsInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer state;
	private Integer[] allState;
	//专门给首页信息公示栏
	public String title;
	public String subtitle;
	public String keywords;
	public String descr;
	public String content;
	public String editors ;
	public int clickCount;
	//判断是前台还是后台传过去的
	public String whereFrom;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer[] getAllState() {
		return allState;
	}
	public void setAllState(Integer[] allState) {
		this.allState = allState;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEditors() {
		return editors;
	}
	public void setEditors(String editors) {
		this.editors = editors;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public String getWhereFrom() {
		return whereFrom;
	}
	public void setWhereFrom(String whereFrom) {
		this.whereFrom = whereFrom;
	}
	
}