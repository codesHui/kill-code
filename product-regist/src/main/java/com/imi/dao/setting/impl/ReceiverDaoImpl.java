package com.imi.dao.setting.impl;

import org.springframework.stereotype.Component;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.setting.IReceiverDao;
import com.imi.entity.setting.CommonMessage;

@Component("receiverDao")
public class ReceiverDaoImpl extends BaseDaoImpl<CommonMessage> implements IReceiverDao {


}
