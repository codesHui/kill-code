package com.imi.dao.news.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.news.NewsInfo;
import com.imi.dao.news.INewsDao;
import com.imi.entity.products.News;
/**
 * 消息数据接口实现类。
 */
@Repository
public class NewsDaoImpl extends BaseDaoImpl<News> implements INewsDao {
	private static final Logger logger = Logger.getLogger(NewsDaoImpl.class);
	
	@Override
	public List<News> findAllNews(NewsInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询News数据...");
		Map<String, Object> parameters = new HashMap<String, Object>();
		String hql = "select n from News n where 1=1"; 
		if(info.getAllState()!=null && info.getAllState().length!=0){
			 hql+=" and n.state in (:allstatus)";
			 parameters.put("allstatus",info.getAllState());
		}else if(StringUtils.isEmpty(info.getWhereFrom())){
			 hql+=" and n.state in (:allstatus)";
			 parameters.put("allstatus",new Integer[]{1,2,3,5,6,7,8});
		}
		hql+=" order by n.createTime desc";
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	 
	@Override
	public Long total(NewsInfo info) {
		if(logger.isDebugEnabled()) logger.debug("统计News数据...");
		Map<String, Object> parameters = new HashMap<String, Object>();
		String hql = "select count(*) from News n where 1=1";
		if(info.getAllState()!=null && info.getAllState().length!=0){
			 hql+=" and n.state in (:allstatus)";
			 parameters.put("allstatus",info.getAllState());
		}
		return this.count(hql, parameters);
	}
	
	@Override
	public News findById(Long id) {
		if(logger.isDebugEnabled()) logger.debug("通过Id查找News...");
		return this.load(News.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> findLatestNews(Integer start,Integer end,Integer[] state) {
		if(logger.isDebugEnabled()) logger.debug("查询最新News数据...");
		String hql = "select n from News n where 1=1";
		if(state.length!=0){
			hql+=" and n.state in (:allstatus)";
		}
		hql+=" order by n.createTime desc";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameterList("allstatus", state);
		query.setFirstResult(start);
		query.setMaxResults(end);
		return  query.list();
	}
	 
}