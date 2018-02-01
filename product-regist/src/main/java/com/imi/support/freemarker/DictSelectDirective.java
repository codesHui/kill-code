package com.imi.support.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * select下拉框FreeMarker自定义指令
 * 
 * @author 菠萝大象
 */
@Component(value="dictSelect")
public class DictSelectDirective implements TemplateDirectiveModel {
	@Resource
	private DictContext dictContext;
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		SimpleScalar id = (SimpleScalar) params.get("id"); // select id
		SimpleScalar name = (SimpleScalar) params.get("name"); // select name
		SimpleScalar type = (SimpleScalar) params.get("type"); // 数据字典类型
		SimpleScalar code = (SimpleScalar) params.get("code"); // 判断角色
		TemplateModel value = (TemplateModel)params.get("value"); // 数据字典值
		SimpleScalar style = (SimpleScalar) params.get("class"); // select class样式
		SimpleScalar onChange = (SimpleScalar) params.get("onChange"); // select onChange
		TemplateBooleanModel showSelect = (TemplateBooleanModel) params.get("showSelect"); // 是否显示请选择option
		SimpleScalar option = (SimpleScalar) params.get("option"); //默认的第一个显示文本，如果为空，显示"请选择"，否则按此值显示
		
		SimpleScalar headValue = (SimpleScalar) params.get("headValue"); //默认的第一个显示文本，如果为空，显示"请选择"，否则按此值显示
		Writer out = env.getOut();
		if (type != null) {
			StringBuilder builder = new StringBuilder();
			builder.append("<select id='");
			if (id != null && StringUtils.isNotBlank(id.getAsString())) {
				builder.append(id);
			} else if (name != null && StringUtils.isNotBlank(name.getAsString())) {
				builder.append(name);
			}
			builder.append("'");
			if (name != null && StringUtils.isNotBlank(name.getAsString())) {
				builder.append(" name='").append(name).append("'");
			}
			if (style != null && StringUtils.isNotBlank(style.getAsString())) {
				builder.append(" class='").append(style).append("'");
			}
			if (onChange != null && StringUtils.isNotBlank(onChange.getAsString())) {
				builder.append(" onChange='").append(onChange).append("'");
			}
			builder.append(">");
			if (showSelect == null || showSelect.getAsBoolean() != false) {
				builder.append("<option value='");
				if(headValue!=null&& StringUtils.isNotBlank(headValue.getAsString())){
					builder.append(headValue.getAsString());	
				}
				builder.append("'>");
				// 如果默认显示值不为空，即需要显示特定的文本，则加入option值
				if(option != null && StringUtils.isNotBlank(option.getAsString()))
					builder.append(option);
				else
					builder.append("请选择");
				builder.append("</option>");
			}

			List<String[]> dictList = dictContext.getDict(type.getAsString());
			
			if (dictList != null) {
				//添加过滤只有系统管理员可以新增登记会员
				if(code!=null){
					//添加过滤只有系统管理员可以新增登记会员和系统管理员
					System.out.println("系统管理员可以新增登记会员"+dictList.size());
					if(!"sysAdmin".equals(code.getAsString())){
						for (int j=0;j<dictList.size();j++) {
							if("icrm".equals(dictList.get(j)[0])){
								dictList.remove(dictList.get(j));
								continue;
							}
							if("sysAdmin".equals(dictList.get(j)[0])){
								dictList.remove(dictList.get(j));
							}
						}
					}
					if(!"bjsAdmin".equals(code.getAsString()) && !"sysAdmin".equals(code.getAsString())){
					////添加过滤初、高、中级管理员不能添加保交所
						System.out.println("保交所用户角色设置"+dictList.size());
						for (int i=0; i<dictList.size(); i++) {
							if("bjsAdmin".equals(dictList.get(i)[0])){
								dictList.remove(dictList.get(i));
							}
						}
					}
					
					
				}
			
				for (String[] s : dictList) {
					builder.append("<option value='").append(s[0]).append("'");
					if(null!=value){
					String	values = ((SimpleScalar)value).getAsString();
					if(values.equalsIgnoreCase(s[0])){
						builder.append(" selected");	
						}
					}
			builder.append(">").append(s[1]).append("</option>");
					
				}
			}
			builder.append("</select>");
			out.write(builder.toString());
		}
	}

	public DictContext getDictContext() {
		return dictContext;
	}
	public void setDictContext(DictContext dictContext) {
		this.dictContext = dictContext;
	}
	
	
}
