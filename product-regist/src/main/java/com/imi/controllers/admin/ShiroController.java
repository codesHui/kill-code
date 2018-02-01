package com.imi.controllers.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.service.security.IMenuService;
import com.imi.service.security.IUserAuthentication;
import com.softvan.model.Json;

/**
 * Shiro 控制器。
 * @author josh.
 * @since 2014-05-14.
 */
@Controller
@RequestMapping(value={"/admin"})
public class ShiroController {
	private static final Logger logger = Logger.getLogger(ShiroController.class);
	private static final String SESSION_KEY_VERIFYCODE = "__verifyCode__";
	//注入菜单服务接口。
	@Resource
	private IMenuService menuService;
	//注入用户验证服务接口。
	@Resource
	private IUserAuthentication userAuthentication;
	/**
	 * 加载登录页面。
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载登录页面...");
		model.addAttribute("systemName", this.menuService.loadSystemName());
		return "shiro/login";
	}
	/**
	 * 加载验证码图片。
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/verifyCodeImage", method = RequestMethod.GET)
	public synchronized void verifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(logger.isDebugEnabled())logger.debug("加载验证码图片...");
		//设置页面不缓存。
		response.setHeader("Pragme", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		//生成验证码。
		String code = this.userAuthentication.createVerifyCode();
		//将验证码放到HttpSession里面。
		request.getSession().setAttribute(SESSION_KEY_VERIFYCODE, code);
		if(logger.isDebugEnabled())logger.debug(String.format("本次生成验证码为[%s],已存放到Http Session中...", code));
		//设置输出的内容为JPEG图像。
		response.setContentType("image/jpeg");
		//写入输出流。
		ImageIO.write(this.userAuthentication.loadVerifyCodeImage(code), "JPEG", response.getOutputStream());
	}
	/**
	 * 验证用户。
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Json authentication(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("验证用户...");
		Json result = new Json();
		try{
			String account = WebUtils.getCleanParam(request, "account"),
					  password = WebUtils.getCleanParam(request, "password"),
					  submitCode = WebUtils.getCleanParam(request, "verifycode");
			 String browser =	request.getHeader("User-Agent");
			 if(!StringUtils.isEmpty(browser) && browser.length() > 255){ browser = browser.substring(0, 254);  }
			//获取HttpSession中的验证码。
			String code = (String)request.getSession().getAttribute(SESSION_KEY_VERIFYCODE);
			if(logger.isDebugEnabled())logger.debug(String.format("用户[%1$s,%2$s]登录时输入的验证码为[%3$s],HttpSession中的验证码为[%4$s]",account,password,submitCode,code));
			if(StringUtils.isEmpty(submitCode) ||  !submitCode.equalsIgnoreCase(code)){
				if(logger.isDebugEnabled()) logger.debug(String.format("验证码不正确！［%1$s => %2$s］", submitCode,code));
				throw new RuntimeException("验证码不正确！");
			}
			this.userAuthentication.authentication(account, password, HelperController.getRemoteAddr(request), browser);
			result.setSuccess(true);
			result.setMsg("验证通过！");
		}catch(Exception e){
			if(logger.isDebugEnabled()) logger.error(String.format("登录验证失败:%s",e.getMessage()), e);
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 用户注销。
	 * @return
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(){
		if(logger.isDebugEnabled()) logger.debug("用户注销...");
		this.userAuthentication.logout();
		return "redirect:/front/index";
	}
}