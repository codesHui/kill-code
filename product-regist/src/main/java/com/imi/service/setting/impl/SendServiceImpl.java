package com.imi.service.setting.impl;

import java.util.List;

import com.imi.entity.security.User;
import com.imi.entity.setting.CommonMessage;
import com.imi.service.setting.SenderService;

public class SendServiceImpl implements SenderService {

	@Override
	public void sendNotification(CommonMessage senderNotification) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CommonMessage> listAllNotificationByUser(User user, Integer page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByRecervers(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CommonMessage> findChatRecord(CommonMessage mess) {
		// TODO Auto-generated method stub
		return null;
	}

}
