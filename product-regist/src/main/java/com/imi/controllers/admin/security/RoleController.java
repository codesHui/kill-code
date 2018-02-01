package com.imi.controllers.admin.security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.RoleInfo;
import com.imi.entity.security.Right;
import com.imi.service.security.IRoleService;
import com.imi.support.EnumMapUtils;
import com.softvan.model.DataGrid;
import com.softvan.model.Json;
import com.softvan.service.Status;

/**
 * 角色控制器。

 *
 */
@Controller
@RequestMapping(value = "/admin/security/role")
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	//设置角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_ROLE + ":" + Right.DELETE);
		return "security/role_list";
	}
	/**
	 * 获取编辑页面。
	 * @return
	 * 编辑页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.roleService.loadStatusName(status.getValue()));	
		}
		model.addAttribute("statusMap", statusMap);
		return "security/role_edit";
	}
	/**
	 * 获取角色权限页面。
	 * @return
	 * 角色权限页面。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/right/{roleId}", method = RequestMethod.GET)
	public String roleRight(@PathVariable String roleId, Model model){
		if(logger.isDebugEnabled()) logger.debug("加载角色权限页面...");
		model.addAttribute("current_role_id", roleId);
		return "security/role_right";
	}
	/**
	 * 加载角色权限ID数据。
	 * @param roleId
	 * 角色ID。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/right/{roleId}/ids", method = RequestMethod.GET)
	@ResponseBody
	public Long[] loadRoleRightIds(@PathVariable Long roleId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载角色［%s］权限ID数据...", roleId));
		Long [] ids = this.roleService.loadRoleRightIds(roleId);
		return ids == null ? new Long[0] : ids;
	}
	/**
	 * 更新角色权限。
	 * @param roleId
	 * 所属角色ID。
	 * @param rightIds
	 * 角色权限ID集合。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/{roleId}/rights", method = RequestMethod.POST)
	@ResponseBody
	public Json updateRoleRights(@PathVariable Long roleId,@RequestBody Long[] rightIds){
		Json json = new Json();
		try {
			this.roleService.updateRoleRights(roleId, rightIds);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(String.format("更新角色权限时发生异常：%s", e.getMessage()), e);
		}
		return json;
	}
	/**
	 * 获取全部的角色数据。
	 * @return
	 */
	@RequestMapping(value="/all", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<RoleInfo> all(){
		if(logger.isDebugEnabled()) logger.debug("加载全部的角色数据...");
		return this.roleService.loadAll();
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RoleInfo> datagrid(RoleInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.roleService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(RoleInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			result.setData(this.roleService.update(info));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新角色数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_ROLE + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据： %s ...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.roleService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:%s", e.getMessage()), e);
		}
		return result;
	}
}