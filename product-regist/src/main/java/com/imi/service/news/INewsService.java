package com.imi.service.news;

import java.util.List;

import com.imi.base.model.news.NewsInfo;
import com.imi.entity.products.News;
/**
 * 消息服务接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface INewsService {
	
	List<News> findAllNews(NewsInfo info);
	
	List<News> findLatestNews(Integer start,Integer end,Integer[] state);
	 
	public Long total(NewsInfo info);
	 
	News findById(Long id);
	
	void saveNews(int state,Long createId,String subtitle,String content);
	
	
	String findLatestNewsJson();

	void insertProductNews(int state, String title, String subtitle, Long currentUserId);
	
	void deleteNews(News news);
}