package com.imi.controllers.admin.security;

import java.util.Arrays;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.base.model.security.LoginLogInfo;
import com.imi.entity.security.Right;
import com.imi.service.security.ILoginLogService;
import com.softvan.model.DataGrid;
import com.softvan.model.Json;
/**
 * 登录日志管理控制器。
 * @author yangyong.
 * @since 2014-05-19.
 */
@Controller
@RequestMapping(value = "/admin/security/log")
public class LogController {
	private static final Logger logger = Logger.getLogger(LogController.class);
	//登录日志服务。
	@Resource
	private ILoginLogService loginLogService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.VIEW})
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		model.addAttribute("PER_DELETE", ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.DELETE);
		return "security/log_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.VIEW})
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<LoginLogInfo> datagrid(LoginLogInfo info){
		if(logger.isDebugEnabled()) logger.debug("加载列表数据...");
		return this.loginLogService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequiresPermissions({ModuleConstant.SECURITY_LOGIN_LOG + ":" + Right.DELETE})
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(@RequestBody String[] ids){
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据 %s...", Arrays.toString(ids)));
		Json result = new Json();
		try {
			this.loginLogService.delete(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error(String.format("删除数据时发生异常:", e.getMessage()), e);
		}
		return result;
	}
}