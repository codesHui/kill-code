package com.imi.support.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 数据字典根据key显示value
 * 
 * @author 菠萝大象
 */
@Component(value="dictDisplay")
public class DictDisplayDirective implements TemplateDirectiveModel {

	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		SimpleScalar paramValue = (SimpleScalar) params.get("value");
		SimpleScalar type = (SimpleScalar) params.get("type");
	//	System.out.println("paramValue-----------------"+paramValue);
	//	System.out.println("type-----------------"+type);
		Writer out = env.getOut();
		String result = null;
		if (paramValue != null)
			result = DictContext.getDict(type.getAsString(), paramValue.getAsString());
		
		if (StringUtils.isEmpty(result))
			result = "";
		out.write(result);
	}

	
	
}