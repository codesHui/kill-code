package com.imi.controllers.admin;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工具控制器。
 * @author josh.
 * @since 2014-06-10.
 */
@Controller
@RequestMapping(value = "/admin")
public class HelperController {

	/**
	 * 构建UUID字符串。
	 * @return
	 * UUID字符串。
	 */
	@RequestMapping(value = "/UUID", method = RequestMethod.GET)
	@ResponseBody
	public String[] buildUUID(Integer count){
		if(count == null || count < 1) count = 1;
		String[] result = new String[count];
		for(int i = 0; i < count; i++){
			 result[i] = UUID.randomUUID().toString();
		} 
		return result;
	}
	
	/**
	 * 获取客户端IP地址。
	 * @param request
	 * 客户端请求对象。
	 * @return 客户端IP地址。
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		if(request == null) throw new IllegalArgumentException("request");
		String client =  request.getHeader("x-forwarded-for");
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client =  request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client  = request.getHeader("WL-Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client = request.getRemoteAddr();
		}
		return client;
	}
}