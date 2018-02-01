package com.imi.service.security.impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.imi.base.model.security.MenuInfo;
import com.imi.base.service.impl.BaseDataServiceImpl;
import com.imi.dao.security.IMenuDao;
import com.imi.entity.security.Menu;
import com.imi.service.security.IMenuService;
import com.softvan.configuration.ModuleDefine;
import com.softvan.configuration.ModuleParse;
import com.softvan.configuration.ModuleSystem;
import com.softvan.model.DataGrid;
/**
 * 菜单服务。

 */

public class MenuServiceImpl extends BaseDataServiceImpl<Menu,MenuInfo>  implements IMenuService {
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private IMenuDao menuDao;
	private String menuFile,systemId,systemName;
	/**
	 * 设置菜单数据接口。
	 * @param menuDao
	 * 菜单数据接口。
	 */
	public void setMenuDao(IMenuDao menuDao) {
		if(logger.isDebugEnabled()) logger.debug("注入菜单数据接口...");
		this.menuDao = menuDao;
	}
	/**
	 * 设置菜单文件。
	 * @param menuFile
	 * 菜单文件。
	 */
	public void setMenuFile(String menuFile) {
		if(logger.isDebugEnabled()) logger.debug("注入菜单文件...");
		this.menuFile = menuFile;
	}
	/**
	 * 设置菜单文件中系统ID。
	 * @param systemId
	 * 系统ID。
	 */
	public void setSystemId(String systemId) {
		if(logger.isDebugEnabled()) logger.debug("注入当前系统ID...");
		this.systemId = systemId;
	}
	/**
	 * 设置系统名称。
	 * @param systemName 
	 *	  系统名称。
	 */
	public void setSystemName(String systemName) {
		if(logger.isDebugEnabled()) logger.debug("注入系统名称...");
		this.systemName = systemName;
	}
	/*
	 * 加载系统名称。
	 * @see com.softvan.netplatform.service.admin.IMenuService#loadSystemName()
	 */
	@Override
	public String loadSystemName() {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载系统名称［%s］...", this.systemName));
		return this.systemName;
	}
	/*
	 * 加载全部菜单数据。
	 * @see com.softvan.netplatform.service.admin.security.IMenuService#loadAllMenus()
	 */
	@Override
	public List<MenuInfo> loadAllMenus() {
		if(logger.isDebugEnabled()) logger.debug("加载全部菜单数据...");
		return this.changeModel(this.menuDao.loadTopMenus());
	}
	/*
	 * 重载。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#datagrid(java.lang.Object)
	 */
	@Override
	public DataGrid<MenuInfo> datagrid(MenuInfo info) {
		DataGrid<MenuInfo> grid = new DataGrid<MenuInfo>();
		grid.setRows(this.changeModel(this.find(info)));
		return grid;
	}
	/*
	 * 查询数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Menu> find(MenuInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.menuDao.findMenus(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected MenuInfo changeModel(Menu data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Menu => MenuInfo ...");
		if(data == null) return null;
		MenuInfo info = new MenuInfo();
		BeanUtils.copyProperties(data, info, new String[]{"children"});
		if(data.getChildren() != null && data.getChildren().size() > 0){
			Set<MenuInfo> children = new TreeSet<MenuInfo>();
			for(Menu menu : data.getChildren()){
				if(menu == null) continue;
				MenuInfo child = this.changeModel(menu);
				child.setPid(info.getId());
				children.add(child);
			}
			if(children.size() > 0) info.setChildren(children);
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(MenuInfo info) {
		 throw new RuntimeException("未实现！");
	}
	/*
	 * 更新数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public MenuInfo update(MenuInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		return this.changeModel(this.updateMenu(info));
	}
	//更新菜单数据。
	private Menu updateMenu(MenuInfo info){
		if(info == null) return null;
		boolean isAdded = false;
		Menu menu = this.menuDao.load(Menu.class, info.getId());
		if(isAdded = (menu == null)){
			//if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			menu = new Menu();
		}
		BeanUtils.copyProperties(info, menu);
		if(!StringUtils.isEmpty(info.getPid()) && (menu.getParent() == null || !menu.getParent().getId().equals(info.getPid()))){
			Menu parent = this.menuDao.load(Menu.class, info.getPid());
			if(parent != null)menu.setParent(parent);
		}
		if(isAdded) this.menuDao.save(menu);
		return menu;
	}
	/*
	 * 删除数据。
	 * @see com.softvan.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Menu data = this.menuDao.load(Menu.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s", data.getName()));
				this.menuDao.delete(data); 
			}
		}
	}
	/*
	 *  初始化菜单数据。
	 * @see com.softvan.netplatform.service.admin.security.IMenuService#init()
	 */
	@Override
	public void init() throws Exception {
		
		if(logger.isDebugEnabled()) logger.debug("开始初始化菜单数据...");
		if(StringUtils.isEmpty(this.systemId)) throw new RuntimeException("系统ID为空！");
		if(StringUtils.isEmpty(this.menuFile)) throw new RuntimeException("菜单配置文件为空！");
		System.out.println(this.getClass().getResource("").getPath());
		ClassPathResource resource = new ClassPathResource(this.menuFile);
		
		
		if(!resource.exists()) throw new RuntimeException("菜单配置文件不存在！");
		ModuleSystem data = ModuleParse.loadModuleSystem(resource.getInputStream(), this.systemId);
		if(data == null) throw new RuntimeException(String.format("模块系统［%s］不存在！", this.systemId));
		if(data.getModules() != null && data.getModules().size() > 0){
			if(logger.isDebugEnabled()) logger.debug("添加菜单数据...");
			for(ModuleDefine define : data.getModules()){
				if(define == null) continue;
				this.addMenu(define, null);
			}
		}
	}
	//添加菜单数据。
	private void addMenu(ModuleDefine define, ModuleDefine parent){
		if(logger.isDebugEnabled()) logger.debug(String.format("添加菜单：%s", define));
		if(define == null || StringUtils.isEmpty(define.getId())) return;
		Long defineid=Long.parseLong(define.getId());
		Menu menu = this.menuDao.load(Menu.class, defineid);
		if(menu == null){
			menu = new Menu();
			
		
			menu.setId(defineid);
			
		}
		menu.setIcon(define.getIcon());
		menu.setName(define.getName());
		menu.setUri(define.getUri());
		menu.setOrderNo(define.getOrder());
		if(parent != null && !parent.getId().equalsIgnoreCase(define.getId())){
			Long defineparentid=Long.parseLong(define.getId());
			menu.setParent(this.menuDao.load(Menu.class, defineparentid));
		}
		this.menuDao.saveOrUpdate(menu);
		//子菜单。
		if(define.getModules() != null && define.getModules().size() > 0){
			for(ModuleDefine m : define.getModules()){
				if(m == null) continue;
				this.addMenu(m, define);
			}
		}
	}
}