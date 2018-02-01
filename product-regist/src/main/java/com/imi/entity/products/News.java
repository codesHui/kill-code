package com.imi.entity.products;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.imi.base.BaseEntity;

/**
 * 通知消息
 */
@Entity
@Table(name = "biz_news")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE,region = "biz_news")
public class News extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public String title;

	//public Dictionary dic;

	public Integer state;

	public String subtitle;

	public String keywords;

	public String descr;

	public String content;

	public String editors ;

	public int clickCount;
 
	@Column(name = "title")

	public String getTitle() {
		return title;
	}

/*	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "channle_id" ,referencedColumnName="dic_key")
	public Dictionary getDic() {
		return dic;
	}*/

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	@Column(name = "subtitle")

	public String getSubtitle() {
		return subtitle;
	}

	@Column(name = "keywords")

	public String getKeywords() {
		return keywords;
	}

	@Column(name = "description")

	public String getDescr() {
		return descr;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY, optional = true)
	@Column(name = "content")

	public String getContent() {
		return content;
	}

	@Column(name = "editors")

	public String getEditors() {
		return editors;
	}

	@Column(name = "click_count")

	public int getClickCount() {
		return clickCount;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*public void setDic(Dictionary dic) {
		this.dic = dic;
	}
*/
	public void setState(Integer state) {
		this.state = state;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setEditors(String editors) {
		this.editors = editors;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

}
