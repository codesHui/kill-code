package com.imi.interceptors;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imi.entity.security.Role;
import com.imi.entity.security.User;
import com.imi.service.security.IUserAuthorization;
import com.softvan.aware.IUserAware;
/**
 * 用户认证拦截器。
 */
public class UserAuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(UserAuthenticationInterceptor.class);
	private static final String SESSION_KEY_ROLES = "sessionRoles";
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	private IUserAuthorization userAuthorization;
	/**
	 * 设置用户授权服务接口。
	 * @param userAuthorization
	 * 用户授权服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		this.userAuthorization = userAuthorization;
	}
	/*
	 * 在业务处理之前被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("开始业务处理..."+request.getServletPath());
			this.startTimeThreadLocal.set(System.currentTimeMillis());//线程绑定开始时间(该数据只有当前请求的线程可见)。
		}
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = (HandlerMethod)handler;
			if(hm != null && (hm.getBean() instanceof IUserAware)){
				IUserAware userAware = (IUserAware)hm.getBean();
				if(logger.isDebugEnabled())logger.debug("准备注入用户信息...");
				Subject subject = SecurityUtils.getSubject();
				String account = (String)subject.getPrincipal();
				if(!StringUtils.isEmpty(account)){
					User user = this.userAuthorization.loadUserByAccount(account);
					if(user != null){
						userAware.setUserId(user.getId());
						userAware.setUserName(user.getName());
						userAware.setUserNickName(user.getNickName());
						Object sessionRoles = request.getSession().getAttribute(SESSION_KEY_ROLES);
						Set<Role> roleSet=null;
						
						if(sessionRoles!=null){
							roleSet=(Set<Role>) sessionRoles;
							}else{
							 roleSet=user.getRoles();
							 request.getSession().setAttribute(SESSION_KEY_ROLES, roleSet);
						}
						
						
						Role role= new Role();
						if (roleSet != null) {
							Iterator<Role> it = roleSet.iterator();
							/* userAware.setRoleCode(roleSet); */
							while (it.hasNext()) {
								role=it.next();
								
								break;
							}
						}
						userAware.setRoleCode(role.getCode());
						
						
						if(logger.isDebugEnabled())logger.debug(String.format("注入[%1$s]用户信息:id=%2$s;name=%3$s;nick=%4$s;rolecode=%5$s;", account, user.getId(), user.getName(), user.getNickName(),role.getCode()));
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	/*
	 * 业务处理完全处理完后被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		super.afterCompletion(request, response, handler, ex);
		if(logger.isDebugEnabled()){
			long consumeTime = System.currentTimeMillis() - this.startTimeThreadLocal.get();
			logger.debug("业务"+request.getServletPath()+"处理完成，耗时：" + consumeTime + "  " + ((consumeTime > 500) ? "[较慢]" : "[正常]"));
		}
	}
}