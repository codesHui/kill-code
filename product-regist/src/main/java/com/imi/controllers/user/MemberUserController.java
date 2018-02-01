package com.imi.controllers.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imi.controllers.admin.security.ModuleConstant;
import com.imi.entity.security.Right;
import com.imi.entity.security.User;
import com.imi.entity.setting.MemberUser;
import com.imi.model.member.MemberUserInfo;
import com.imi.service.security.IMemberUserService;
import com.imi.service.security.IUserAuthentication;
import com.imi.service.security.IUserService;
import com.softvan.aware.IUserAware;
import com.softvan.model.Json;
/**
 * 主险种管理控制器。
 * @author wanmei.
 * @since 2014-06-02.
 */
@Controller
@RequestMapping(value = "/muser")
public class MemberUserController  implements IUserAware  {
	private static final Logger logger = Logger.getLogger(MemberUserController.class);
	//注入产品服务接口。
	@Resource
	private IMemberUserService memberUserService;
	@Resource
	private IUserService userService;
	//注入用户验证服务接口。
	@Resource
	private IUserAuthentication userAuthentication;
	
	private Long current_user_id;
	private String roleCode;
	private String userNickName;
	/**
	 * 获取列表页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/detail"})
	public String detail(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		request.setAttribute("PER_UPDATE", ModuleConstant.SECURITY_USER + ":" + Right.UPDATE);
		request.setAttribute("PER_DELETE", ModuleConstant.SECURITY_USER + ":" + Right.DELETE);
	
		MemberUser muser = new MemberUser();
		MemberUserInfo memberUserInfo = new MemberUserInfo();
	    User user=  new User();
		/*if(StringUtils.isEmpty(current_user_id)){
			return "redirect:/front/index "; 
		}
		*/
		user.setId(current_user_id);
		memberUserInfo.setUser(user);
		muser = memberUserService.findMemberUser(memberUserInfo);
		
		request.setAttribute("muser",muser);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("roleCode",roleCode);
		request.setAttribute("current_user_id",current_user_id);
		
		return "muser_detail";
	}
	/**
	 * 修改密码。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/modifyPassword"}, method = RequestMethod.POST)
	@ResponseBody
	public Json modifyPassword(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		Json result = new Json();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		try {
			userService.modifyPassword(current_user_id, oldPassword, newPassword);
			result.setMsg("1");
		} catch (Exception e) {
			result.setMsg("0");
		}
		result.setSuccess(true);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("userNickName",userNickName);
		return result;
	}
	/**
	 * 获取修改密码页面。
	 * @return
	 */
	//@RequiresPermissions({ModuleConstant.SECURITY_USER + ":" + Right.VIEW})
	@RequestMapping(value={"/modifyPassword_init"})
	public String modifyPassword_init(HttpServletRequest request){
		if(logger.isDebugEnabled()) logger.debug("加载列表页面...");
		
		User user = new User();
		user = userService.findById(current_user_id);
		request.setAttribute("userNickName",userNickName);
		request.setAttribute("current_user_id",current_user_id);
		request.setAttribute("oldPassword", user.getPassword());
		
		return "muser_modifyPassword";
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
	
	@Override
	public void setUserName(String userName) {
	
		System.out.println(userName);
	}

	@Override
	public void setUserNickName(String userNickName) {
		// TODO Auto-generated method stub
		System.out.println(userNickName);
		this.userNickName=userNickName;
	}

	@Override
	public void setUserId(Long userId) {
		current_user_id = userId;
		
	}


	public void setRoleCode(String roleCodes) {
		System.out.println(roleCodes);
		roleCode  = roleCodes;
	}



	public IMemberUserService getMemberUserService() {
		return memberUserService;
	}



	public IUserAuthentication getUserAuthentication() {
		return userAuthentication;
	}



	public void setMemberUserService(IMemberUserService memberUserService) {
		this.memberUserService = memberUserService;
	}



	public void setUserAuthentication(IUserAuthentication userAuthentication) {
		this.userAuthentication = userAuthentication;
	}
	
	
	
	

}