package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * 访客标签。
 */
public class GuestTag extends SecureTag {
	private static final Logger logger = Logger.getLogger(GuestTag.class);
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		 if(this.getSubject() == null || this.getSubject().getPrincipal() == null){
			  logger.debug("Subject does not exist or does not have a known identity (aka 'principal'). Tag body will be evaluated.");
			 this.renderBody(env, body);
		 }else { 
			logger.debug("Subject exists or has a known identity (aka 'principal'). Tag body will not be evaluated.");
		}
	}

}