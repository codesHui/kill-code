package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * 非认证标签。
 * @author josh.
 * @since 2014-05-16.
 */
public class NotAuthenticatedTag extends SecureTag {
	private static final Logger logger = Logger.getLogger(NotAuthenticatedTag.class);
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		Subject subject = this.getSubject();
		if(subject == null || !subject.isAuthenticated()){
			logger.debug("Subject does not exist or is not authenticated.Tag body will be evaluated.");
			this.renderBody(env, body);
		}else {
			logger.debug("Subject exists and is authenticated. Tag body will not be evaluated.");
		}
	}

}