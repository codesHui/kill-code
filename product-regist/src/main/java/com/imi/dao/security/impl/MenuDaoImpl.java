package com.imi.dao.security.impl;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.security.MenuInfo;
import com.imi.dao.security.IMenuDao;
import com.imi.entity.security.Menu;

/**
 * 菜单数据操作实现类。
 */
@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao{
	private static final Logger logger = Logger.getLogger(MenuDaoImpl.class);
	/*
	 * 加载一级菜单。
	 */
	@Override
	public List<Menu> loadTopMenus() {
		if(logger.isDebugEnabled()) logger.debug("加载一级菜单...");
		final String hql = "from Menu m where (m.parent is null) order by m.orderNo asc";
		return this.find(hql, null, null, null);
	}
	/*
	 * 查询数据。
	 */
	@Override
	public List<Menu> findMenus(MenuInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Menu m where (m.parent is null) ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(hql, info, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by m." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, null, null);
	}
	//添加查询条件。
	private String addWhere(String hql,MenuInfo info, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and ((m.id like :name) or (m.name like :name) or (m.uri like :name)) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
	/*
	 * 删除数据。
	 */
	@Override
	public void delete(Menu data) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		int count = 0;
		if(data.getRights() != null && (count = data.getRights().size()) > 0){
			throw new RuntimeException(String.format("菜单已与［%d］权限相关联！", count));
		}
		if(data.getChildren() != null && (count = data.getChildren().size()) > 0){
			throw new RuntimeException(String.format("菜单［%1$s］下有［%2$d］子菜单！",data.getName(), count));
		}
		super.delete(data);
	}
}