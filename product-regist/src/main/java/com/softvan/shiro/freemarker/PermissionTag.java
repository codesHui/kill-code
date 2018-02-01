package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * 权限标签基类。
 * @author josh.
 * @since 2014-05-16.
 */
public abstract class PermissionTag extends SecureTag {
	/**
	 * 检查是否有权限显示。
	 * @param p
	 * @return
	 */
	protected abstract boolean showTagBody(String p);
	/**
	 * 当前用户是否有权限。
	 * @param p
	 * 权限字符串。
	 * @return
	 */
	protected boolean isPermitted(String p){
		Subject subject = this.getSubject();
		if(subject != null){
			return subject.isPermitted(p);
		}
		return false;
	}
	/**
	 * 获取name的值。
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getName(Map params){
		return this.getParam(params, "name");
	}
	/*
	 * 验证参数。
	 * @see com.softvan.shiro.freemarker.SecureTag#verifyParameters(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void verifyParameters(Map params) throws TemplateModelException{
		String permission = this.getName(params);
		if(StringUtils.isEmpty(permission)){
			throw new TemplateModelException("the 'name' tag attribute must be set.");
		}
	}
	/*
	 * (non-Javadoc)
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException,TemplateException{
		boolean show = this.showTagBody(this.getName(params));
		if(show){
			this.renderBody(env, body);
		}
	}
}