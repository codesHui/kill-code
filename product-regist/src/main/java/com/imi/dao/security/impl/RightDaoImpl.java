package com.imi.dao.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.security.RightInfo;
import com.imi.dao.security.IRightDao;
import com.imi.entity.security.Right;

/**
 * 基本权限数据接口实现。
 */
@Repository("rightDao")
public class RightDaoImpl extends BaseDaoImpl<Right> implements IRightDao {
	private static final Logger logger = Logger.getLogger(RightDaoImpl.class);
	/*
	 * 查询数据。
	 */
	@Override
	public List<Right> findRights(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Right r where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据汇总。
	 */
	@Override
	public Long total(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据汇总...");
		String hql = "select count(*) from Right r where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(RightInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (r.name like :name)";
			parameters.put("name", info.getName());
		}
		return hql;
	}
}