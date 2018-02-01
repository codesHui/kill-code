package com.imi.service.security;

import java.util.List;

import com.imi.base.model.security.MenuRightInfo;
import com.imi.base.service.IBaseDataService;
import com.softvan.model.TreeNode;

/**
 * 菜单权限服务。
 * @author josh.
 * @since 2014-05-04.
 */
public interface IMenuRightService extends IBaseDataService<MenuRightInfo> {
	/**
	 * 菜单权限初始化。
	 * @throws Exception
	 */
	void init() throws Exception;
	/**
	 * 加载全部的菜单权限树。
	 * @return
	 * 菜单权限树。
	 */
	List<TreeNode> loadAllMenuRights();
}