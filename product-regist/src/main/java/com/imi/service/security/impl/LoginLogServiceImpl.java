package com.imi.service.security.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.imi.base.model.security.LoginLogInfo;
import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.security.ILoginLogDao;
import com.imi.entity.security.LoginLog;
import com.imi.service.security.ILoginLogService;
/**
 * 登录日志服务实现。
 */

public class LoginLogServiceImpl extends BaseDataServiceImpl<LoginLog, LoginLogInfo> implements ILoginLogService {
	private static final Logger logger = Logger.getLogger(LoginLogServiceImpl.class);
	private ILoginLogDao loginLogDao;
	/**
	 * 设置登录日志数据接口。
	 * @param loginLogDao
	 * 数据接口。
	 */
	public void setLoginLogDao(ILoginLogDao loginLogDao) {
		if(logger.isDebugEnabled()) logger.debug("注入登录日志数据接口...");
		this.loginLogDao = loginLogDao;
	}
	/*
	 * 查询数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<LoginLog> find(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.loginLogDao.findLoginLogs(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected LoginLogInfo changeModel(LoginLog data) {
		if(logger.isDebugEnabled()) logger.debug(" 数据模型转换 LoginLog => LoginLogInfo");
		if(data == null) return null;
		LoginLogInfo info = new LoginLogInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.loginLogDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public LoginLogInfo update(LoginLogInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		LoginLog data = StringUtils.isEmpty(info.getId()) ? null : this.loginLogDao.load(LoginLog.class, info.getId());
		if(isAdded = (data == null)){
			
			data = new LoginLog();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.loginLogDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			LoginLog data = this.loginLogDao.load(LoginLog.class, ids[i]);
			if(data != null) this.loginLogDao.delete(data);
		}
	}
}