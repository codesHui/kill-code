package com.imi.dao.security;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.security.MenuInfo;
import com.imi.entity.security.Menu;
/**
 * 菜单数据操作接口。
 * @author josh.
 *	@since 2014-04-28.
 */
public interface IMenuDao extends IBaseDao<Menu> {
	/**
	 * 加载一级菜单集合。
	 * @return
	 */
	List<Menu> loadTopMenus();
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 菜单集合。
	 */
	List<Menu> findMenus(MenuInfo info);
}