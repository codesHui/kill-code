package com.imi.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.imi.entity.security.User;

/**
 * 身份认证拦截器
 * @author fengwei.
 * @since 2015年1月21日 下午2:41:35.
 */
public class IdentityAuthenticationInterceptor  extends HandlerInterceptorAdapter {
	private static Logger logger = Logger.getLogger(IdentityAuthenticationInterceptor.class);
	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");


	
	/*
	 * 在业务处理之前被调用。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if(logger.isDebugEnabled()){
			logger.debug("开始前台业务处理..."+request.getServletPath());
			this.startTimeThreadLocal.set(System.currentTimeMillis());//线程绑定开始时间(该数据只有当前请求的线程可见)。
		}
		 //1、请求到登录页面 放行  

	    	User user = (User) request.getSession().getAttribute("user");
	    		
	    		if(user != null){	//用户已经登录
	    		  logger.info("前台业务用户账号："+user.getAccount());
	    		  logger.info("前台业务用户类型："+user.getType());	
	    		   if (user.getType()!=2){
	    		  		response.sendRedirect(request.getContextPath()+"/index");	
	    		  	}	
	    		  	return false;
	    		}else{
		    		logger.info("用户没session");	
		    	}
		
		    	
		    	
		  
		    
		   
		
	 
	          
	  
	    //4、非法请求 即这些请求需要登录后才能访问  
	    //重定向到登录页面  
	    //记录上一次的地址
/*	    Cookie cookie = new Cookie("LastPage",request.getRequestURI().substring(request.getContextPath().length()));
	    cookie.setPath(request.getContextPath());
	    response.addCookie(cookie);*/
	    //TODO 增加了项目名称
	    //获取abbr 
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
			logger.debug("前台业务"+request.getServletPath()+"处理完成，耗时：" + consumeTime + "  " + ((consumeTime > 500) ? "[较慢]" : "[正常]"));
		}
	}
	
	
}