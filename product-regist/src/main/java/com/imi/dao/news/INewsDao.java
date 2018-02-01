package com.imi.dao.news;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.news.NewsInfo;
import com.imi.entity.products.News;

/**
 * 用户数据接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface INewsDao extends IBaseDao<News> {
	List<News> findAllNews(NewsInfo info);
	 
	List<News> findLatestNews(Integer start,Integer end,Integer[] state);
	
	Long total(NewsInfo info);
	 
	News findById(Long id);
}