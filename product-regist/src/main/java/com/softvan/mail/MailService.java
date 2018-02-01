package com.softvan.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MailService 基类.
 */

public abstract class MailService  {
	protected static final Log log = LogFactory.getLog(MailService.class);

	FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * 发送SimpleMailMessage的接口.
	 */
	public abstract void send(SimpleMailMessage msg);

	/**
	 * 简易发信接口.
	 */
	public void send(String from, String to, String subject, String text) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(text);
		send(msg);
	}
	
	/**
	 * 使用模版发送HTML格式的邮件.
	 *
	 * @param msg		   装有to,from,subject信息的SimpleMailMessage
	 * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
	 * @param model		   渲染模版所需的数据
	 */
	public abstract void send(SimpleMailMessage msg, String templateName, Map model);

	/**
	 * 使用Freemarker 根据模版生成邮件内容.
	 */
	public String generateEmailContent(String templateName, Map map) {
		try {
			Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
		} catch (TemplateException e) {
			log.error("Error while processing FreeMarker template ", e);
		} catch (FileNotFoundException e) {
			log.error("Error while open template file ", e);
		} catch (IOException e) {
			log.error("Error while generate Email Content ", e);
		}
		return null;
	}

	public FreeMarkerConfigurer getFreeMarkerConfigurer() {
		return freeMarkerConfigurer;
	}

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.freeMarkerConfigurer = freeMarkerConfigurer;
	}
	
	
}