package com.imi.controllers.admin.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.MenuInfo;
import com.imi.entity.security.Right;
import com.imi.service.security.IMenuService;
import com.softvan.model.Json;
import com.softvan.model.TreeNode;
/**
 * 菜单管理控制器。
 * @author yangyong.
 * @since 2014-04-28.
 */
@Controller
@RequestMapping(value = "/admin/security/menu")
public class MenuController {
	private static final Logger logger = Logger.getLogger(MenuController.class);
	//菜单服务接口。
	@Resource
	private IMenuService menuService;
	/**
	 * 菜单列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_MENU + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_MENU + ":" + Right.DELETE);
		return "security/menu_list";
	}
	/**
	 * 列表数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.VIEW})
	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public List<MenuInfo> datagrid(MenuInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.menuService.datagrid(info).getRows();
	}
	/**
	 * 菜单树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/all/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> tree(){
		if(logger.isDebugEnabled()) logger.debug("加载全部菜单树结构数据..");
		List<TreeNode> result = new ArrayList<TreeNode>();
		List<MenuInfo> menus = this.menuService.loadAllMenus();
		if(menus != null && menus.size() > 0){
			for(MenuInfo menu : menus){
				if(menu == null) continue;
				TreeNode node = this.createTreeNode(menu);
				if(node != null) result.add(node);
			}
		}
		return result;
	}
	//创建节点。
	private TreeNode createTreeNode(MenuInfo menu){
		if(menu == null) return null;
		TreeNode node = new TreeNode(menu.getId().toString(), menu.getName());
		if(menu.getChildren() != null && menu.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<TreeNode>();
			for(MenuInfo info : menu.getChildren()){
				if(info == null) continue;
				TreeNode e = this.createTreeNode(info);
				if(e != null) children.add(e);
			}
			if(children.size() > 0) node.setChildren(children);
		}
		return node;
	}
	/**
	 * 初始化菜单数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.UPDATE})
	@RequestMapping(value = "/init", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Json init(){
		if(logger.isDebugEnabled()) logger.debug("初始化菜单数据...");
		Json result = new Json();
		try {
			this.menuService.init();
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化菜单时发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU + ":" + Right.DELETE})
	@RequestMapping(value= "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] id){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...",Arrays.toString(id)));
		Json result = new Json();
		try {
			this.menuService.delete(id);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}