package com.imi.service.security.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.imi.base.model.security.RightInfo;
import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.security.IRightDao;
import com.imi.entity.security.Right;
import com.imi.service.security.DefaultRightType;
import com.imi.service.security.IRightService;

/**
 * 基础权限服务接口实现。
 */

public class RightServiceImpl extends BaseDataServiceImpl<Right,RightInfo> implements IRightService {
	private static final Logger logger = Logger.getLogger(RightServiceImpl.class);
	private IRightDao rightDao;
	private Map<Integer, String> rightNameMap;
	/**
	 * 设置基础权限数据接口。
	 * @param rightDao
	 * 基础权限数据接口。
	 */
	public void setRightDao(IRightDao rightDao) {
		if(logger.isDebugEnabled()) logger.debug("注入基础权限数据接口...");
		this.rightDao = rightDao;
	}
	/**
	 * 设置权限名称集合。
	 * @param rightNameMap
	 * 权限名称集合。
	 */
	public void setRightNameMap(Map<Integer, String> rightNameMap) {
		if(logger.isDebugEnabled()) logger.debug("注入权限名称集合...");
		this.rightNameMap = rightNameMap;
	}
	/*
	 * 查询数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Right> find(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.rightDao.findRights(info);
	}
	/*
	 * 数据模型类型转换。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RightInfo changeModel(Right data) {
		if(logger.isDebugEnabled()) logger.debug(" 数据模型类型转换 Right => RightInfo");
		if(data == null) return null;
		RightInfo info = new RightInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
    /*
     * 查询数据统计。
     * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
     */
	@Override
	protected Long total(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.rightDao.total(info);
	}
    /*
     * 更新数据。
     * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
     */
	@Override
	public RightInfo update(RightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		 return this.changeModel(this.updateRight(info));
	}
	//更新权限。
	private Right updateRight(RightInfo info){
		if(info == null) return null;
		boolean isAdded = false;
		Right right = StringUtils.isEmpty( info.getId()) ? null : this.rightDao.load(Right.class, Long.parseLong(info.getId()));
		if(isAdded = (right == null)){
		//	if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			right = new Right();
		}
		
	/*	ConvertUtils.register(
				
				
				
				new Converter(){   //注册类型转换器
			 
			   }, String.class); */
		BeanUtils.copyProperties(info, right);
		//private String id,name;
		//private int value,orderNo;
		//private String name;
		//private int value,orderNo;
		
		if(isAdded)this.rightDao.save(right);
		return right;
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
			Right data = this.rightDao.load(Right.class, ids[i]);
			if(data != null) {
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", data.getName()));
				this.rightDao.delete(data);
			}
		}
	}
	/*
	 * 加载全部权限数据集合。
	 * @see com.softvan.netplatform.service.admin.security.IRightService#loadAllRights()
	 */
	@Override
	public List<RightInfo> loadAllRights() {
		if(logger.isDebugEnabled()) logger.debug("加载全部权限数据集合...");
		return this.changeModel(this.find(new RightInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() { return "orderNo"; }
			@Override
			public String getOrder() {return "asc";}
		}));
	}
	/*
	 * 初始化数据。
	 * @see com.softvan.netplatform.service.admin.IRightService#init()
	 */
	@Override
	public void init() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("初始化权限数据...");
		if(this.rightNameMap == null || this.rightNameMap.size() == 0) throw new Exception("未设置默认权限名称设置！");
		for(DefaultRightType right : DefaultRightType.values()){
			 String rightName = this.rightNameMap.get(right.getValue());
			 if(StringUtils.isEmpty(rightName)) rightName = String.format("%s", right);
			 if(logger.isDebugEnabled()) logger.debug(String.format("初始化［%1$d］［%2$s］权限...", right.getValue(), rightName));
			 this.updateRight(new RightInfo(String.format("%d", right.getValue()), rightName,right.getValue()));
		}
	}
}