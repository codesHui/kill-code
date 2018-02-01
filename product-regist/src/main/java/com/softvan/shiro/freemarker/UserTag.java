package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * 登录用户标签。
 */
public class UserTag extends SecureTag {
	private static final  Logger logger = Logger.getLogger(UserTag.class);
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		 Subject subject = this.getSubject();
		 if(subject != null && subject.getPrincipal() != null){
			 logger.debug("Subject has known identity (aka 'principal'). Tag body will be evaluated.");
			 this.renderBody(env, body);
		 }else {
			logger.debug("Subject does not exist or have a known identity (aka 'principal'). Tag body will not be evaluated.");
		}
	}

}