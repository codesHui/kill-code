package com.imi.controllers.admin.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imi.controllers.admin.HelperController;
import com.imi.service.security.IMenuRightService;
import com.imi.service.security.IMenuService;
import com.imi.service.security.IRightService;
import com.imi.service.security.IRoleService;
import com.imi.service.security.IUserService;

/**
 * 权限初始化控制器。
 * @author yangyong.
 * @since 2014-06-30.
 */
@Controller
@RequestMapping("/admin/security/init")
public class InitController {
	private static final Logger logger = Logger.getLogger(InitController.class);
	//菜单服务接口。 
	@Resource
	private IMenuService menuService;
	//基础权限服务接口。
	@Resource
	private IRightService rightService;
	//菜单权限服务接口。
	@Resource
	private IMenuRightService menuRightService;
	//角色服务接口。
	@Resource
	private IRoleService roleService;
	//用户服务接口。
	@Resource
	private IUserService userService;
	/**
	 * 初始化。
	 */
	@RequestMapping(value = {"","/"}, method={RequestMethod.GET, RequestMethod.POST})
	public String goalInit(HttpServletRequest request, Model model){
		StringBuilder msgBuilder = new StringBuilder();
		try{
			String client = HelperController.getRemoteAddr(request);
			msgBuilder.append("client:").append(client).append("\r\n");
			if(logger.isDebugEnabled()) logger.debug(String.format("［ client => %s］", client));
			if(!"127.0.0.1".equalsIgnoreCase(client) && !"localhost".equalsIgnoreCase(client)){
				throw new Exception(String.format("为安全考虑，本请求只能在服务器上操作！(client => %s)", client));
			}
			String msg = null;
			logger.info(msg = "权限初始化开始....");
			msgBuilder.append(msg).append("\r\n");
			logger.info(msg = "开始初始化菜单数据...");
			msgBuilder.append(msg).append("\r\n");
			this.menuService.init();
			logger.info(msg = "完成菜单数据初始化！");
			msgBuilder.append(msg).append("\r\n");
			logger.info(msg = "开始基础权限数据初始化...");
			msgBuilder.append(msg).append("\r\n");
			this.rightService.init();
			logger.info(msg = "完成基础权限数据初始化！");
			msgBuilder.append(msg).append("\r\n");
			logger.info(msg = "开始菜单权限初始化...");
			msgBuilder.append(msg).append("\r\n");
			this.menuRightService.init();
			logger.info(msg = "完成菜单权限数据初始化!");
			msgBuilder.append(msg).append("\r\n");
		/*	logger.info(msg = "开始角色初始化....");
			msgBuilder.append(msg).append("\r\n");
			String roleId = "1",account = "admin",password = "123456";
 			this.roleService.init(Long.parseLong(roleId));
			logger.info(msg = "完成角色数据初始化!［roleId:"+ roleId +"］");
			msgBuilder.append(msg).append("\r\n");
			logger.info(msg = "开始用户初始化....");
			msgBuilder.append(msg).append("\r\n");
			this.userService.init(Long.parseLong(roleId), account, password);
			logger.info(msg = "完成用户初始化.[账号："+ account+"  密码："+ password +"]");
			msgBuilder.append(msg).append("\r\n");*/
		}catch(Exception e){
			e.printStackTrace();
			String err = null;
			logger.info(err = ("初始化时异常：" + e.getMessage()));
			logger.error(e);
			msgBuilder.append(err).append("\r\n");
		}finally{
			logger.info("初始化结束！");
		}
		model.addAttribute("MESSAGE", msgBuilder.toString());
		return "security/init";
	}
}