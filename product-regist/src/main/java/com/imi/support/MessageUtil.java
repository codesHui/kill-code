package com.imi.support;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 字典项转换
 */
@Component
public class MessageUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(MessageUtil.class);

	private static MessageSource messageSource;

	@Autowired(required = true)
	public MessageUtil(@Qualifier("messageSource") MessageSource messageSource) {
		MessageUtil.messageSource = messageSource;
	}




	
	
	

	/**
	 * 本地测试检查
	 */
	private static void checkMessageSource() {
		if (messageSource == null) {
			logger.debug("messageSource is null. Inject messageSource from application context.");
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:spring-context.xml");
			messageSource = (MessageSource) context.getBean("messageSource");
		}
	}

	public static String getMessage(String messageId) {
		return getMessage(messageId, null, Locale.getDefault());
	}

	public static String getMessage(String messageId, String... params) {
		return getMessage(messageId, params, Locale.getDefault());
	}

	public static String getMessage(String messageId, Object[] params,
			Locale locale) {
		checkMessageSource();
		return messageSource.getMessage(messageId, params, locale);
	}

	public static String getMessage(String messageId, Object[] params,
			String arg, Locale locale) {
		checkMessageSource();
		return messageSource.getMessage(messageId, params, arg, locale);
	}
}