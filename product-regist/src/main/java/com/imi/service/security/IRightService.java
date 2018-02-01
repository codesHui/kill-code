package com.imi.service.security;

import java.util.List;

import com.imi.base.model.security.RightInfo;
import com.imi.base.service.IBaseDataService;

/**
 * 基础权限服务接口。
 * @author josh.
 * @since 2014-05-03.
 */
public interface IRightService extends IBaseDataService<RightInfo> {
	/**
	 * 初始化数据。
	 * @throws Exception
	 */
	void init() throws Exception;
	/**
	 * 加载全部权限数据集合。
	 * @return 全部权限数据集合。
	 */
	List<RightInfo> loadAllRights();
}