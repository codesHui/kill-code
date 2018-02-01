package com.imi.dao.security;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.security.RoleInfo;
import com.imi.entity.security.Role;

/**
 * 角色数据接口。

 */
public interface IRoleDao extends IBaseDao<Role> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Role> findRoles(RoleInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RoleInfo info);
	
	Role findBycode(String code);
}