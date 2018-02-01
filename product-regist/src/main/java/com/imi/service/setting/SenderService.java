package com.imi.service.setting;

import java.util.List;

import com.imi.entity.security.User;
import com.imi.entity.setting.CommonMessage;

public interface SenderService
		 {
	void sendNotification(CommonMessage senderNotification);
	
	List<CommonMessage> listAllNotificationByUser(User user, Integer page);

	void delete(User user);

	void deleteByRecervers(User user);

	List<CommonMessage> findChatRecord(CommonMessage mess);
}