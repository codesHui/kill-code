package com.imi.service.security;

import java.util.List;

import com.imi.base.model.security.RoleInfo;
import com.imi.base.service.IBaseDataService;
import com.imi.entity.security.Role;
/**
 * 角色服务接口。
 * @author josh.
 * @since 2014-05-06.
 */
public interface IRoleService extends IBaseDataService<RoleInfo> {
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载全部角色数据集合。
	 * @return
	 */
	List<RoleInfo> loadAll();
	/**
	 * 加载角色权限集合。
	 * @param roleId
	 * 角色ID。
	 * @return
	 */
	Long[] loadRoleRightIds(Long roleId);
	/**
	 * 加载角色数据。
	 * @param roleId
	 * 角色ID。
	 * @return
	 * 角色数据。
	 */
	Role loadRole(Long roleId);
	/**
	 * 数据模型转换。
	 * @param role
	 * 角色数据。
	 * @return
	 * 角色信息。
	 */
	RoleInfo conversion(Role role);
	/**
	 * 初始化角色。
	 * @param roleId
	 * 角色ID。
	 * @throws Exception
	 */
	void init(Long roleId) throws Exception;
	/**
	 * 更新角色权限。
	 * @param roleId
	 * 角色ID。
	 * @param rightIds
	 * 权限ID集合。
	 */
	void updateRoleRights(Long roleId,Long[] rightIds) throws Exception;
	
	Role findBycode(String code);
}