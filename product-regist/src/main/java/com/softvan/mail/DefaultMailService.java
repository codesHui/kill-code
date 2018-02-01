package com.softvan.mail;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * MailService接口的实现.
 */
public class DefaultMailService extends MailService {

	JavaMailSender mailSender;

	/**
	 * 发送SimpleMailMessage.
	 */
	@Override
	public void send(SimpleMailMessage msg) {
		try {
			mailSender.send(msg);
			log.info("email 发送成功!!!!");
		} catch (MailException e) {
			log.error(e.getMessage(), e);
		}
	}

	

	/**
	 * 使用模版发送HTML格式的邮件.
	 *
	 * @param msg		   装有to,from,subject信息的SimpleMailMessage
	 * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
	 * @param model		   渲染模版所需的数据
	 */
	@Override
	public void send(SimpleMailMessage msg, String templateName, Map model) {
		// 生成html邮件内容
		String content = generateEmailContent(templateName, model);
		MimeMessage mimeMsg;
		try {
			mimeMsg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
			helper.setTo(msg.getTo());
			helper.setSubject(msg.getSubject());
			helper.setFrom(msg.getFrom());
			helper.setText(content, true);
		} catch (MessagingException ex) {
			log.error(ex.getMessage(), ex);
		}
		msg.setText(content);
		mailSender.send(msg);
	}



	public JavaMailSender getMailSender() {
		return mailSender;
	}



	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	

	
	/**
	 * Sends HTML emails. Requires a sendto valid email address, a subject, the
	 * originator's merchant id, a map that contains key-value pairs that will
	 * be parsed in the HTML template, as well as the html template file to be
	 * used
	 * 
	 * @param sendto
	 * @param subject
	 * @param merchantid
	 * @param keyvalueparseableelements
	 * @param emailtemplatename
	 * @throws Exception
	 */
	


}