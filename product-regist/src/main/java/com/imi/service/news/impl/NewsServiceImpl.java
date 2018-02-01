package com.imi.service.news.impl;
 
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.imi.base.model.news.NewsInfo;
import com.imi.dao.news.INewsDao;
import com.imi.dao.security.IUserDao;
import com.imi.entity.products.News;
import com.imi.entity.security.User;
import com.imi.service.news.INewsService;
import com.imi.support.Constants;

/**
 * 消息服务接口实现。
 */
@Service
public class NewsServiceImpl  implements INewsService {
	private static final Logger logger = Logger.getLogger(NewsServiceImpl.class);
	public static final String FORMAT_YMD = "yyyy-MM-dd";
	@Resource
	private INewsDao newsDao;
	
	@Resource
	private IUserDao userDao;
	@Override
	public List<News> findAllNews(NewsInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询News数据...");
		return this.newsDao.findAllNews(info);
	}
	@Override
	public Long total(NewsInfo info) {
		// TODO Auto-generated method stub
		return this.newsDao.total(info);
	}
	@Override
	public News findById(Long id) {
		// TODO Auto-generated method stub
		return this.newsDao.findById(id);
	}
	/*消息接口
	  state:    对应Constants 里的【消息表单初始状态】
	  createId: 当前用户ID
	  title:    栏目名称或标题
	  subtitle: 子标题
	  content:  内容
	*/
	@Override
	public void saveNews(int state,Long createId,String subtitle,String content) {
		// TODO Auto-generated method stub
		News news=new News();
		
		switch (state) {
		case 1:
			news.setTitle("产品注册通知");
			break;
		case 2:
			news.setTitle("产品注销通知");
			break;
		case 3:
			news.setTitle("产品问题通知");
			break;
		case 4:
			news.setTitle("产品警告通知");
			break;
		case 5:
			news.setTitle("注册平台简报");
			break;
		case 6:
			news.setTitle("协会处理通报");
			break;
		case 7:
			news.setTitle("监督行政处理");
			break;
		case 8:
			news.setTitle("政策法规");
			break;
		case 9:
			news.setTitle("相关新闻");
			break;
		default:
			break;
		}
		 
		news.setState(state);
		news.setCreateTime(new Date());
		User user = new User();
		user.setId(createId);
		news.setCreator(user);
		news.setSubtitle(subtitle);
		news.setContent(content);
		this.newsDao.save(news);
	}
	@Override
	public List<News> findLatestNews(Integer start,Integer end,Integer[] state) {
		// TODO Auto-generated method stub
		return this.newsDao.findLatestNews(start,end,state);
	}

	@Override
	public String findLatestNewsJson() {
		SimpleDateFormat sf = new SimpleDateFormat(FORMAT_YMD);
		StringWriter stringWriter = new StringWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonGenerator g = objectMapper.getJsonFactory()
					.createJsonGenerator(stringWriter);

			g.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
			g.writeStartObject();
			g.writeArrayFieldStart("message");
			List<News> message = findLatestNews(0, 12, new Integer[] {Constants.REGIST_NEWS,Constants.CANCEL_NEWS,Constants.PROBLEM_NEWS,Constants.REPORT_NEWS,Constants.DEAL_NEWS,Constants.ADMIN_NEWS,Constants.ABOUTUS_NEWS});
			for (News one : message) {
				g.writeStartObject();
				g.writeStringField("newsId", String.valueOf(one.getId()));
				g.writeStringField("title", "•" + one.getSubtitle());
				g.writeStringField("createTime", sf.format(one.getCreateTime()));
				g.writeEndObject();
			}
			g.writeEndArray();

			g.writeArrayFieldStart("layer");
			List<News> layer = findLatestNews(0, 12, new Integer[] {Constants.POLICY_NEWS});
			for (News one : layer) {
				g.writeStartObject();
				g.writeStringField("newsId", String.valueOf(one.getId()));
				g.writeStringField("title", "•" + one.getSubtitle());
				g.writeStringField("createTime", sf.format(one.getCreateTime()));
				g.writeEndObject();
			}
			g.writeEndArray();
			g.writeEndObject();
			g.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stringWriter.toString();
	}
	@Override
	public void insertProductNews(int state, String title, String subtitle,
			Long currentUserId) {
		News oneNews= new News();
		oneNews.setState(state);
		oneNews.setContent(subtitle);
		oneNews.setSubtitle(subtitle);
		oneNews.setTitle(title);
		oneNews.setKeywords(title);
		oneNews.setClickCount(1);
		oneNews.setCreateTime(new Date());
		User creator=userDao.get(currentUserId);
		if(creator!=null){
		oneNews.setEditors(creator.getNickName());
		oneNews.setCreator(creator);
		}

		this.newsDao.save( oneNews);

	}
	@Override
	public void deleteNews(News news) {
		// TODO Auto-generated method stub
		this.newsDao.delete(news);
	}
	
}