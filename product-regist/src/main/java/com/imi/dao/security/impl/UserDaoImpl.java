package com.imi.dao.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.security.UserInfo;
import com.imi.dao.security.IUserDao;
import com.imi.entity.security.User;
/**
 * 用户数据接口实现类。
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.softvan.netplatform.dao.admin.IUserDao#findUsers(com.softvan.netplatform.model.admin.UserInfo)
	 */
	@Override
	public List<User> findUsers(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "select u from User u where 1=1 "; 
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.softvan.netplatform.dao.admin.IUserDao#total(com.softvan.netplatform.model.admin.UserInfo)
	 */
	@Override
	public Long total(UserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from User u where 1=1";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(UserInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getAccount())){
			hql += " and (u.account = :account) ";
			parameters.put("account", info.getAccount());
		}
		if(info.getRoleId() != null && info.getRoleId().length > 0){
			hql += " and (u.roles.id in (:roleId)) ";
			parameters.put("roleId", info.getRoleId());
		}
		if(info.getStatus() != null){
			hql += " and (u.status = :status)";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getName())){
			hql +=" and (u.name like :name  or u.account like :name  or u.nickName like :name)";
			parameters.put("name", "%" + info.getName() +  "%");
		}
		return hql;
	}
	/*
	 * 根据账号查找用户。
	 * @see com.softvan.netplatform.dao.admin.IUserDao#findByAccount(java.lang.String)
	 */
	@Override
	public User findByAccount(String account) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号查询用户：%s", account));
		if(StringUtils.isEmpty(account)) return null;
		final String hql = "from User u where u.account = :account order by u.createTime desc ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("account", account);
		List<User> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 重载删除数据。
	 * @see com.softvan.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(User data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
	/*	if(data.getAgencies() != null && (count = data.getAgencies().size()) > 0){
			throw new RuntimeException(String.format("用户［%1$s］关联［%2$d］机构，暂不能删除！", data.getName(), count));
		}
		if(data.getTopics() != null && (count = data.getTopics().size()) > 0){
			throw new RuntimeException(String.format("用户［%1$s］关联［%2$d］教师答疑主题，暂不能删除！", data.getName(), count));
		}
		if(data.getDetails() != null && (count = data.getDetails().size()) > 0){
			throw new RuntimeException(String.format("用户［%1$s］关联［%2$d］教师答疑回复，暂不能删除！", data.getName(), count));
		}
		if(data.getOrders() != null && (count = data.getOrders().size()) > 0){
			throw new RuntimeException(String.format("用户［%1$s］关联［%2$d］订单，暂不能删除！", data.getName(), count));
		}*/
		super.delete(data);
	}
	@Override
	public User findById(Long id) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据id查询用户：%s", id));
		if(StringUtils.isEmpty(id)) return null;
		final String hql = "from User u where u.id = :id order by u.createTime desc ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		List<User> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
}