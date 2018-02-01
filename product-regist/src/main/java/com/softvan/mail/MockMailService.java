package com.softvan.mail;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;

/**
 * 虚拟的MailService.
 * 测试用途,将邮件内容打印在log里面而不是真正发信.
 */

public class MockMailService extends MailService {

	/**
	 * 发送SimpleMailMessage
	 */
	@Override
	public void send(SimpleMailMessage msg) {
		StringBuffer mail = new StringBuffer();
		mail.append(getMailHeader(msg) + "\n");
		mail.append(msg.getText());
		log.info(mail.toString());
		log.info("email 发送成功!!!!");
	}

	/**
	 * 使用模版发送HTML格式的邮件
	 *
	 * @param msg		  装有to,from,subject信息的SimpleMailMessage
	 * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
	 * @param model		渲染模版所需的数据
	 */
	@Override
	public void send(SimpleMailMessage msg, String templateName, Map model) {
		StringBuffer mail = new StringBuffer();
		mail.append(getMailHeader(msg) + "\n");
		mail.append(generateEmailContent(templateName, model));
		log.info(mail.toString());
	}

	protected String getMailHeader(SimpleMailMessage msg) {
		StringBuffer header = new StringBuffer();

		header.append("To: ");
		for (String to : msg.getTo())
			header.append(to).append(";");
		header.append("\nFrom: " + msg.getFrom());
		header.append("\nSubject: " + msg.getSubject());
		return header.toString();
	}
}