package com.softvan.shiro.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * 基于freemarker的Shiro安全标签基础类。
 * @author josh.
 * @since 2014-05-15.
 */
public abstract class SecureTag implements TemplateDirectiveModel {
	 /**
	  * 执行。
	  * @param env
	  * @param params
	  * @param loopVars
	  * @param body
	  * @throws TemplateException
	  * @throws IOException
	  */
	@Override
	@SuppressWarnings("rawtypes")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException{
		this.verifyParameters(params);
		this.render(env, params, body);
	}
	/**
	 * 绘制标签抽象方法。
	 * @param env
	 * @param params
	 * @param body
	 * @throws TemplateException
	 */
	@SuppressWarnings("rawtypes")
	public abstract void render(Environment env, Map params, TemplateDirectiveBody body) throws IOException,TemplateException ;
	/**
	 * 获取参数值。
	 * @param params
	 * 参数集合。
	 * @param name
	 * 参数名称。
	 * @return
	 * 参数值。
	 */
	@SuppressWarnings("rawtypes")
	protected String getParam(Map params, String name){
		Object value = params.get(name);
		if(value instanceof SimpleScalar){
			return ((SimpleScalar)value).getAsString();
		}
		return null;
	}
	/**
	 * 获取当前用户。
	 * @return
	 * 当前用户。
	 */
	protected Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	/**
	 * 验证参数。
	 * @param params
	 * 参数集合。
	 * @throws TemplateModelException
	 */
	@SuppressWarnings("rawtypes")
	protected void verifyParameters(Map params) throws TemplateModelException {
		
	}
	/**
	 * 绘制标签体。
	 * @param env
	 * 
	 * @param body
	 * 
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected void renderBody(Environment env, TemplateDirectiveBody body) throws TemplateException, IOException{
		if(body != null){
			body.render(env.getOut());
		}
	}
}