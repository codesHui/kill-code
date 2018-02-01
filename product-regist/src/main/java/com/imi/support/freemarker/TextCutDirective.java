package com.imi.support.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.imi.support.DirectiveUtils;
import com.imi.support.StrUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
 * 功能描述：字符串截取标签


 */
@Component("textCut")
public class TextCutDirective implements TemplateDirectiveModel {
	//例如 ddddddd 截取 len = 2 append= ..  结果 dd..
	public static final String PARAM_S = "value";//字符串
	public static final String PARAM_LEN = "len";//截取长度
	public static final String PARAM_APPEND = "append";//拼接内容
     
	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String s = DirectiveUtils.getString(PARAM_S, params);
		Integer len = DirectiveUtils.getInt(PARAM_LEN, params);
		String append = DirectiveUtils.getString(PARAM_APPEND, params);
		if (s != null) {
			Writer out = env.getOut();
			if (len != null) {
				out.append(StrUtils.textCut(s, len, append));
			} else {
				out.append(s);
			}
		}
	}
}