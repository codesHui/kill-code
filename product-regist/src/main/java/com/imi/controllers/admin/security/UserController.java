package com.imi.controllers.admin.security;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.UserInfo;
import com.imi.entity.security.Right;
import com.imi.service.security.IRoleService;
import com.imi.service.security.IUserService;
import com.imi.support.EnumMapUtils;
import com.softvan.model.DataGrid;
import com.softvan.model.Json;
import com.softvan.service.Gender;
import com.softvan.service.Status;
/**
 * 用户管理控制器。
 * @author yangyong.
 * @since 2014-05-09.
 */
@Controller
@RequestMapping(value = "/admin/security/user")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	//注入用户服务接口。
	@Resource
	private IUserService userService;
	//注入角色服务接口。
	@Resource
	private IRoleService roleService;
	/**
	 * 获取列表页面。
	 * @return
	 */
//	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
		
		Map<String, String> statusMap = EnumMapUtils.createTreeMap();
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.userService.loadStatusName(status.getValue()));	
		}
		model.addAttribute("statusMap", statusMap);
		return "security/user_list";
	}
	/**
	 * 获取编辑页面。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Boolean modify,Model model){
		if(logger.isDebugEnabled()) logger.debug("加载编辑页面...");
		//是否修改
		model.addAttribute("current_is_modify", modify == null ? false : modify);
		
		Map<String, String> statusMap = EnumMapUtils.createTreeMap(), genderMap = EnumMapUtils.createTreeMap();
		//性别
		for(Gender gender : Gender.values()){
			genderMap.put(String.format("%d", gender.getValue()), this.userService.loadGenderName(gender.getValue()));
		}
		model.addAttribute("genderMap", genderMap);
		//状态
		for(Status status : Status.values()){
			statusMap.put(String.format("%d", status.getValue()), this.userService.loadStatusName(status.getValue()));	
		}
		model.addAttribute("statusMap", statusMap);
		
		//角色
		model.addAttribute("all_roles", this.roleService.loadAll());
		
		return "security/user_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserInfo> datagrid(UserInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.userService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
//	@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.UPDATE})
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(UserInfo info){
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		Json result = new Json();
		try {
			 result.setData(this.userService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
		}
		return result;
	}
	/**
	 * 重置用户密码。
	 * @param userId
	 * @param password
	 * @return
	 */
//	@RequiresPermissions(ModuleConstant.SECURITY_USER + ":" +Right.UPDATE)
	@RequestMapping(value="/{userId}/modifyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Json modifyUserPwd(@PathVariable Long userId,String password){
		if(logger.isDebugEnabled())logger.debug(String.format("重置用户［%s］密码...", userId));
		Json result = new Json();
		try {
			 this.userService.modifyPassword(userId, null, password);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新数据发生异常", e);
		}
		return result;
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据: %s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.userService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:", e.getMessage()), e);
		}
		return result;
	}
}