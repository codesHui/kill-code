package com.imi.controllers.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import com.imi.entity.setting.MemberUser;


/**
 * 前台控制器基类
 * 
 */
@Controller
public class FrontBaseController {


	
	
	/**
	 * 获取用户的ID
	 * @param request
	 * @return
	 */
	public Long getUserId(HttpServletRequest request)
	{
		MemberUser user = getMemberUser(request);
		if(user==null) return null;
		return user.getUser().getId();
	}
	/**
	 * 获取机构用户
	 * @param request
	 * @return
	 */
	public MemberUser getMemberUser(HttpServletRequest request)
	{
		return (MemberUser) request.getSession().getAttribute("frontUser");
	}
	
	
	/**
	 * 获取账号信息
	 * @param request
	 * @return
	 */
	public MemberUser getUser(HttpServletRequest request)
	{
		return (MemberUser) request.getSession().getAttribute("adminUser");
	}
}
