package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * 认证标签。
 */
public class AuthenticatedTag extends SecureTag {
	private static final Logger logger = Logger.getLogger(AuthenticatedTag.class);
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException,TemplateException {
		if(this.getSubject() != null && this.getSubject().isAuthenticated()){
			logger.debug("Subject exists and is authenticated."); 
			this.renderBody(env, body);
		}else {
			logger.debug("Subject does not exists or is not authenticated.Tag body will not be evaluated."); 
		}
	}

}