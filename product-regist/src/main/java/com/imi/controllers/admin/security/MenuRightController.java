package com.imi.controllers.admin.security;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.MenuRightInfo;
import com.imi.base.model.security.MenuRightPost;
import com.imi.entity.security.Right;
import com.imi.service.security.IMenuRightService;
import com.imi.service.security.IRightService;
import com.softvan.model.DataGrid;
import com.softvan.model.Json;
import com.softvan.model.TreeNode;
/**
 * 菜单权限控制器。
 * @author yangyong.
 * @since 2014-05-04.
 */
@Controller
@RequestMapping(value = "/admin/security/menu/right")
public class MenuRightController {
	private static final Logger logger = Logger.getLogger(MenuRightController.class);
	//权限服务。
	@Resource
	private IRightService rightService;
	//菜单权限服务。
	@Resource
	private IMenuRightService menuRightService;
	/**
	 * 列表页面。
	 * @return
	 */
	@RequiresPermissions({ ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value = {"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.DELETE);
		return "security/menuright_list";
	}
	/**
	 * 添加页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String edit(String menuId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载添加页面...");
		model.addAttribute("current_menu_id", menuId);
		model.addAttribute("rights", this.rightService.loadAllRights());
		return "security/menuright_add";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<MenuRightInfo> datagrid(MenuRightInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.menuRightService.datagrid(info);
	}
	/**
	 * 加载全部的菜单权限树。
	 * @return
	 */
	@RequestMapping(value="/all/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> loadAllMenuRights(){
		if(logger.isDebugEnabled()) logger.debug("加载全部的菜单权限树...");
		return this.menuRightService.loadAllMenuRights();
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody MenuRightPost post){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			if(post.getMenuId() == null || post.getMenuId().length == 0){
				throw new Exception("菜单ID为空！");
			}
			if(post.getRightId() == null || post.getRightId().length == 0){
				throw new Exception("权限ID为空！");
			}
			for(Long menuId : post.getMenuId()){
				if(StringUtils.isEmpty(menuId)) continue;
				for(Long rightId : post.getRightId()){
					if(StringUtils.isEmpty(rightId)) continue;
					MenuRightInfo info = new MenuRightInfo();
					info.setMenuId(menuId);
					info.setRightId(rightId);
					this.menuRightService.update(info);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新菜单权限时发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_MENU_RIGHT + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] id){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(id)));
		Json result = new Json();
		try {
			this.menuRightService.delete(id);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s",e.getMessage()), e);
		}
		return result;
	}
}