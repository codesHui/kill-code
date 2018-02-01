package com.softvan.shiro.freemarker;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 用户的默认主字符串值或一个特定的主被标记的属性中指定的
 * @author josh.
 * @since 2014-05-16.
 */
public class PrincipalTag extends SecureTag {
	private static final Logger logger = Logger.getLogger(PrincipalTag.class);
	
	@SuppressWarnings("rawtypes")
	private String getType(Map params){
		return this.getParam(params, "type");
	}
	
	@SuppressWarnings("rawtypes")
	private String getProperty(Map params){
		return this.getParam(params, "property");
	}
	
	@SuppressWarnings({"unchecked", "rawtypes" })
	private Object getPrincipalFromClassName(Map params){
		String type = this.getType(params);
		try {
			Class cls = Class.forName(type);
			return this.getSubject().getPrincipals().oneByType(cls);
		} catch (ClassNotFoundException e) {
			 logger.error("Unable to find class for name ["+ type +"]", e);
		}
		return null;
	}
	
	private String getPrincipalProperty(Object principal, String property) throws TemplateModelException{
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(principal.getClass());
			for(PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()){
				 if(propertyDescriptor.getName().equals(property)){
					 Object value = propertyDescriptor.getReadMethod().invoke(principal, (Object[])null);
					 return String.valueOf(value);
				 }
			}
			throw new TemplateModelException("Property ["+property+"] not found in principal of type ["+principal.getClass().getName()+"]");
		} catch (Exception e) {
			throw new TemplateModelException("Error reading property ["+ property +"] from principal of type ["+ principal.getClass().getName() +"]", e);
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException {
		String result = null;
		Subject subject = this.getSubject();
		if(subject != null){
			Object principal;
			if(this.getType(params) == null){
				principal = subject.getPrincipal();
			}else {
				principal = this.getPrincipalFromClassName(params);
			}
			
			if(principal != null){
				String property = this.getProperty(params);
				if(StringUtils.isEmpty(property)){
					result = principal.toString();
				}else {
					result = this.getPrincipalProperty(principal, property);
				}
			}
		}
		
		if(!StringUtils.isEmpty(result)){
			try {
				env.getOut().write(result);
			} catch (IOException e) {
				throw new TemplateException("Error writing ["+result+"] to Freemarker.", e, env);
			}
		}
	}
}