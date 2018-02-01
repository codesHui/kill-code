package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;

/**
 * 角色标签基类。
 * @author josh
 * @since 2014-05-16.
 */
public abstract class RoleTag extends SecureTag {
	/**
	 * 是否显示角色下的标签内容。
	 * @param role
	 * 角色。
	 * @return
	 * 角色是否存在。
	 */
	protected abstract boolean showTagBody(String role);
	/*
	 * 绘制。
	 * @see com.softvan.shiro.freemarker.SecureTag#render(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateDirectiveBody)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException, TemplateException{
		boolean show = this.showTagBody(this.getParam(params, "name"));
		if(show){
			this.renderBody(env, body);
		}
	}
}