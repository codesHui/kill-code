package com.imi.dao.security.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.security.MenuRightInfo;
import com.imi.dao.security.IMenuRightDao;
import com.imi.entity.security.MenuRight;

/**
 * 菜单权限数据接口实现。
 */
@Repository("menuRightDao")
public class MenuRightDaoImpl extends BaseDaoImpl<MenuRight> implements IMenuRightDao {
	private static final Logger logger = Logger.getLogger(MenuRightDaoImpl.class);
	/*
	 * 查询数据。
	 */
	@Override
	public List<MenuRight> findMenuRights(MenuRightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from MenuRight m where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			if(info.getSort().equalsIgnoreCase("menuName")){
				info.setSort("menu.name");
			}else if(info.getSort().equalsIgnoreCase("rightName")){
				info.setSort("right.name");
			}
			hql += " order by m." + info.getSort() + " " + info.getOrder();
		}
		return  this.find(hql, parameters, info.getPage(), info.getRows());
	}
    /*
     * 查询数据汇总。
     */
	@Override
	public Long total(MenuRightInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据汇总...");
		String hql = "select count(*) from MenuRight m where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件到。
	private String addWhere(MenuRightInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getMenuId())){
			hql += " and (m.menu.id = :menuId or m.menu.parent.id = :menuId)";
			parameters.put("menuId", info.getMenuId());
		}
		if(!StringUtils.isEmpty(info.getMenuName())){
			hql += " and (m.menu.name like :menuName)";
			parameters.put("menuName", "%" + info.getMenuName() + "%");
		}
		if(!StringUtils.isEmpty(info.getRightName())){
			hql += " and (m.right.name like :rightName)";
			parameters.put("rightName", "%" + info.getRightName()+ "%");
		}
		return hql;
	}
	/*
	 * 加载菜单权限数据。
	 * @see com.softvan.netplatform.dao.admin.IMenuRightDao#load(com.softvan.netplatform.model.admin.MenuInfo)
	 */
	@Override
	public MenuRight loadMenuRight(Long menuId,Long rightId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载菜单［%1$s］权限［%2$s］数据。...", menuId,rightId));
		if(StringUtils.isEmpty(menuId) || StringUtils.isEmpty(rightId)) return null;
		
		final String hql = "from MenuRight m where (m.menu.id = :menuId) and (m.right.id = :rightId) ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("menuId", menuId);
		parameters.put("rightId", rightId);
		
		List<MenuRight> list = this.find(hql, parameters, null, null);
		if(list != null && list.size() > 0) return list.get(0);
		
		return null;
	}
	/*
	 * 重载删除数据。
	 * @see com.softvan.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(MenuRight data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0; 
		if(data.getRoles() != null && (count = data.getRoles().size()) > 0){
			throw new RuntimeException(String.format("已被［%d］角色关联，暂不能删除", count));
		}
		super.delete(data);
	}
}