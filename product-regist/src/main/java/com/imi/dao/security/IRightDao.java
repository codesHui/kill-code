package com.imi.dao.security;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.security.RightInfo;
import com.imi.entity.security.Right;

/**
 * 基础权限数据接口。
 */
public interface IRightDao extends IBaseDao<Right> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Right> findRights(RightInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RightInfo info);
}