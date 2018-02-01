package com.imi.service.setting.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.imi.dao.setting.IReceiverDao;
import com.imi.entity.security.User;
import com.imi.entity.setting.CommonMessage;
import com.imi.service.setting.ReceiverService;
import com.imi.support.Constants;

@Service("receiverService")
public class ReceiverServiceImpl implements ReceiverService {
	
	@Resource
	private IReceiverDao receiverDao;
	
	

	@Override
	public Integer selectNoReadCount(Long receiveUser) {
		return null;
	}

	@Override
	public Integer selectAllCount(Long receiveUser) {
		return null;
	}

	@Override
	public int updateReadPrimaryKey(CommonMessage record) {
		return 0;
	}

	@Override
	public int deleteMessage(String ids,String type) {
		
		CommonMessage comm = receiverDao.get(Long.parseLong(ids));
		if("1,2".contains(comm.getDeleteFlag()+"")){
			receiverDao.delete(comm);
		}else{
			if("send".equals(type)){
				comm.setDeleteFlag(Constants.MESSAGE_DELETE_SEND);
			}else{
				comm.setDeleteFlag(Constants.MESSAGE_DELETE_RECEIVE);
			}
			receiverDao.update(comm);
		}
		
		return 0;
	}

	@Override
	public int deleteMessage(String[] ids) {
		return 0;
	}
	
	/**
	 * 查询当前登录人的站内信条数
	 */
	@Override
	public int getMessageTotal(Long user_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	COUNT(*) ");
		sql.append(" FROM ");
		sql.append(" 	sys_common_message M ");
		sql.append(" WHERE ");
		sql.append(" 	M.toUser_id = ? ");
		sql.append(" AND M.pId IS NULL ");
		sql.append(" AND M.deleteFlag <> ? ");
		Object obj = receiverDao.findSQLUnique(sql.toString(), user_id,Constants.MESSAGE_DELETE_RECEIVE);
		
		/*String hql = " from CommonMessage c where c.toUser.id = ? and c.pId is null and c.deleteFlag <> ? order by c.createTime desc ";
		List<CommonMessage> list = receiverDao.find(hql, user_id,Constants.MESSAGE_DELETE_RECEIVE);*/
		return obj == null ? 0 : Integer.parseInt(obj.toString());
	}
	
	/**
	 * 查询当前登陆人的收件记录
	 */
	@Override
	public List<CommonMessage> getReceiveList(Long user_id) {
		List<CommonMessage> list = new ArrayList<CommonMessage>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	c.id, ");
		sql.append(" 	c.title, ");
		sql.append(" 	c.state, ");
		sql.append(" 	c.create_time ");
		sql.append(" FROM ");
		sql.append(" 	sys_common_message c ");
		sql.append(" WHERE ");
		sql.append(" 	c.toUser_id = ").append(user_id);
		sql.append(" AND c.pId IS NULL ");
		sql.append(" AND c.deleteFlag <>  ").append(Constants.MESSAGE_DELETE_RECEIVE);
		sql.append(" ORDER BY ");
		sql.append(" 	c.create_time DESC ");
		List<Object[]> objList = null;
		try {
			objList = receiverDao.createSQLQuery(sql.toString()).list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}
		if(objList!=null && objList.size()>0){
			for (int i = 0; i < objList.size(); i++) {
				Object[] obj = objList.get(i);
				CommonMessage mess = new CommonMessage();
				mess.setId(Long.parseLong(obj[0].toString()));
				mess.setTitle(obj[1]== null ? "" : obj[1].toString());
				mess.setState(obj[2]== null ? 1 : Integer.parseInt(obj[2].toString()));
				mess.setCreateTime(obj[3]== null ? new Date():(Timestamp)obj[3]);
				list.add(mess);
			}
		}
		//String hql = " from CommonMessage c where c.toUser.id = ? and c.pId is null and c.deleteFlag <> ? order by c.createTime desc ";
		//List<CommonMessage> list = receiverDao.find(hql, user_id,Constants.MESSAGE_DELETE_RECEIVE);
		return list;
	}

	@Override
	public CommonMessage getMessById(Long id) {
		return receiverDao.get(id);
	}
	
	/**
	 * 发送站内信
	 * title:主题
	 * text :内容
	 * from_id：发送人ID
	 * to_id ： 接收人ID
	 */
	@Override
	public Serializable insertMess(String title, String text, Long from_id, Long to_id) throws Exception {
		CommonMessage mess = new CommonMessage();
		mess.setText(text);
		if(title != null && title.length() > 10){
			title = title.substring(0,9)+"...";
		}else{
			mess.setTitle(title);
		}
		mess.setCreateTime(new Date());
		User fromUser = new User();
		fromUser.setId(from_id);
		mess.setFromUser(fromUser);
		User toUser = new User();
		toUser.setId(to_id);
		mess.setToUser(toUser);
		mess.setStatus(Constants.SEND_YES);
		mess.setState(Constants.READ_NO);
		mess.setIs_deleted_Receiver(Constants.DELETE_NO);
		mess.setReceiveOrSend(Constants.MESSAGE_SEND);
		mess.setSendType(1);
		Long id = (Long) receiverDao.save(mess);
		updateMutualFlag(id, id+"");
		return id;
	}

	@Override
	public void updateReadStateById(Long id) {
		Criteria  c = receiverDao.getSession().createCriteria(CommonMessage.class);
		c.add(Restrictions.eq("id", id));
		List<CommonMessage> list = c.list();
		if(list != null && list.size() > 0){
			CommonMessage com = list.get(0);
			com.setState(Constants.READ_YES);
			com.setModifiedTime(new Date());
			receiverDao.update(com);
		}
	}
	/**
	 * 查询已读数量
	 */
	@Override
	public Integer findReadYes(Long current_user_id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	COUNT(*) ");
		sql.append(" FROM ");
		sql.append(" 	sys_common_message M ");
		sql.append(" WHERE ");
		sql.append(" 	M.toUser_id = ? ");
		sql.append(" AND M.state = ? ");
		sql.append(" AND M.is_deleted_Receiver = ? ");
		sql.append(" AND M.pId IS NULL ");
		sql.append(" AND M.deleteFlag <> ? ");
		Object obj = receiverDao.findSQLUnique(sql.toString(), current_user_id,Constants.READ_YES,Constants.DELETE_NO,Constants.MESSAGE_DELETE_RECEIVE);
		/*String hql = " from CommonMessage c where c.toUser.id = ? and state = ? and is_deleted_Receiver = ? and pId is null and c.deleteFlag <> ? ";
		List<CommonMessage> list = receiverDao.find(hql, current_user_id,Constants.READ_YES,Constants.DELETE_NO,Constants.MESSAGE_DELETE_RECEIVE);*/
		return obj == null ? 0 : Integer.parseInt(obj.toString());
	}

	@Override
	public void insertMessageMutual_shouxin(CommonMessage comm) throws Exception {
		Criteria  c = receiverDao.getSession().createCriteria(CommonMessage.class);
		c.add(Restrictions.eq("id", comm.getId()));
		List<CommonMessage> list = c.list();
		if(list != null && list.size() > 0){
			CommonMessage com2 = list.get(0);
			// 判断收件人是否第一次收到回信
			if(com2.getFirstMutualFlag() == 0){
				// 给收件人生成导航消息列表项
				User u = new User();
				u.setId(com2.getFromUser().getId());
				comm.setToUser(u);
				
				User fromUser = new User();
				fromUser.setId(com2.getToUser().getId());
				comm.setFromUser(fromUser);
				comm.setReceiveOrSend(Constants.MESSAGE_RECEIVE);
				comm.setId(null);
				comm.setpId(null);
				comm.setState(Constants.READ_NO);
				Long saveMenu = (Long) receiverDao.save(comm);
				comm.setMutualFlag(saveMenu+","+com2.getId());
				comm.setFirstMutualFlag(1);
				comm.setTitle(comm.getText());
				receiverDao.update(comm);
				
				
				// 给发件人生成消息历史记录
				/*CommonMessage his = new CommonMessage();
				PropertyUtils.copyProperties(his, comm);
				his.setId(null);
				Long saveHis = (Long) receiverDao.save(his);
				his.setMutualFlag(saveMenu+","+com2.getId());
				his.setpId(com2.getId());
				receiverDao.update(his);*/
				
				com2.setpId(null);
				com2.setMutualFlag(saveMenu+","+com2.getId());
				com2.setFirstMutualFlag(1);
				receiverDao.update(com2);
			}else{
				User u = new User();
				u.setId(com2.getFromUser().getId());
				comm.setToUser(u);
				
				User fromUser = new User();
				fromUser.setId(com2.getToUser().getId());
				comm.setFromUser(fromUser);
				comm.setId(null);
				comm.setpId(com2.getId());
				Long saveMenu = (Long) receiverDao.save(comm);
				comm.setMutualFlag(com2.getMutualFlag());
				receiverDao.update(comm);
			}
			
		}
	}
	
	
	@Override
	public void insertMessageMutual_faxin(CommonMessage comm) throws Exception {
		Criteria  c = receiverDao.getSession().createCriteria(CommonMessage.class);
		c.add(Restrictions.eq("id", comm.getId()));
		List<CommonMessage> list = c.list();
		if(list != null && list.size() > 0){
			CommonMessage com2 = list.get(0);
			User u = new User();
			u.setId(com2.getToUser().getId());
			comm.setToUser(u);
			
			User fromUser = new User();
			fromUser.setId(com2.getFromUser().getId());
			comm.setFromUser(fromUser);
			comm.setId(null);
			comm.setpId(com2.getId());
			comm.setState(Constants.READ_NO);
			Long saveMenu = (Long) receiverDao.save(comm);
			comm.setMutualFlag(com2.getMutualFlag());
			
			receiverDao.update(comm);
		}
	}
	/**
	 * 查询收件箱某信息的聊天记录
	 * type  标记发送记录(send) 还是 收信记录(receive)
	 */
	@Override
	public List<CommonMessage> findReceiverChatRecord(CommonMessage comm)  {
		comm = findById(comm.getId());
		StringBuffer sql = new StringBuffer();
		
		List<CommonMessage> list = new ArrayList<CommonMessage>();
		/*if(comm != null && StringUtils.isNotBlank(comm.getMutualFlag())){*/
			String[] id_list = comm.getMutualFlag().split(",");
			//hql.append(" from CommonMessage c where 1=1 ");
			sql.append(" SELECT ");
			sql.append("   c.id c_id, ");
			sql.append("   c.text, ");
			sql.append("   c.create_time, ");
			sql.append("   u.id u_id, ");
			sql.append("   u.name ");
			sql.append(" FROM ");
			sql.append(" sys_common_message c ");
			sql.append(" LEFT JOIN sys_users u ON c.fromUser_id = u.id ");
			sql.append(" WHERE 1=1 ");
			if(id_list.length == 1){
				sql.append("and ( c.id = '").append(id_list[0]).append("' ");
				sql.append(" OR c.pId in (").append(comm.getMutualFlag()).append(")) ");
			}else if(id_list.length == 2){
				sql.append("and ( ");
				
				sql.append(" c.id in (").append(comm.getMutualFlag()).append(") ");
				
				sql.append(" OR c.pId in (").append(comm.getMutualFlag()).append(") ) ");
			}
			sql.append(" order by c.create_time asc ");
			List<Object[]> objList = null;
			try {
				objList = receiverDao.createSQLQuery(sql.toString()).list();
			} catch (HibernateException e) {
				e.printStackTrace();
				throw e;
			}
			if(objList != null && objList.size()>0){
				for (int j = 0; j < objList.size(); j++) {
					Object[] obj = objList.get(j);
					CommonMessage mess = new CommonMessage();
					mess.setId(Long.parseLong(obj[0].toString()));
					mess.setText(obj[1].toString());
					mess.setCreateTime((Timestamp)obj[2]);
					User u = new User();
					u.setId(Long.parseLong(obj[3].toString()));
					u.setName(obj[4] == null ? "" : obj[4].toString());
					mess.setFromUser(u);
					list.add(mess);
				}
				
			}
		/*}else{
			list.add(comm);
		}*/
		return list;
	}
	
	
	/**
	 * 查询发件箱某个聊天记录
	 */
	@Override
	public List<CommonMessage> findSendChatRecord(CommonMessage comm) {
		comm = findById(comm.getId());
		StringBuffer hql = new StringBuffer();
		List<CommonMessage> list = new ArrayList<CommonMessage>();
		if(comm != null && StringUtils.isNotBlank(comm.getMutualFlag())){
			String[] id_list = comm.getMutualFlag().split(",");
			hql.append(" from CommonMessage c where 1=1 ");
			if(id_list.length == 1){
				hql.append("and ( c.id = '").append(id_list[0]).append("' ");
				hql.append(" OR c.pId in (").append(comm.getMutualFlag()).append(")) ");
			}else if(id_list.length == 2){
				hql.append("and ( ");
				
				hql.append(" c.id in (").append(comm.getMutualFlag()).append(") ");
				
				hql.append(" OR c.pId in (").append(comm.getMutualFlag()).append(") ) ");
			}
			hql.append(" order by c.createTime asc ");
			 list = receiverDao.find(hql.toString());
		}else{
			list.add(comm);
		}
		return list;
	}
	
	/**
	 * 查询当前登录人的所有发件记录
	 * @param user_id
	 * @return
	 */
	@Override
	public List<CommonMessage> findSendInfo(Long user_id) {
		/*String hql = " from CommonMessage c where c.fromUser.id = ? and c.pId is null and c.receiveOrSend = ? and c.sendType <> ? and c.deleteFlag <> ? order by c.createTime desc ";
		List<CommonMessage> list = receiverDao.find(hql, user_id,Constants.MESSAGE_SEND,1,Constants.MESSAGE_DELETE_SEND);
		return list;*/
		
		
		List<CommonMessage> list = new ArrayList<CommonMessage>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	c.id, ");
		sql.append(" 	c.title, ");
		sql.append(" 	c.state, ");
		sql.append(" 	c.create_time ");
		sql.append(" FROM ");
		sql.append(" 	sys_common_message c ");
		sql.append(" WHERE ");
		sql.append(" 	c.fromUser_id = ").append(user_id);
		sql.append(" AND c.pId IS NULL ");
		sql.append(" AND c.receiveOrSend = ").append(Constants.MESSAGE_SEND);
		sql.append(" AND c.sendType <> 1 ");
		sql.append(" AND c.deleteFlag <>  ").append(Constants.MESSAGE_DELETE_SEND);
		sql.append(" ORDER BY ");
		sql.append(" 	c.create_time DESC ");
		List<Object[]> objList = null;
		try {
			objList = receiverDao.createSQLQuery(sql.toString()).list();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw e;
		}
		if(objList!=null && objList.size()>0){
			for (int i = 0; i < objList.size(); i++) {
				Object[] obj = objList.get(i);
				CommonMessage mess = new CommonMessage();
				mess.setId(Long.parseLong(obj[0].toString()));
				mess.setTitle(obj[1]== null ? "" : obj[1].toString());
				mess.setState(obj[2]== null ? 1 : Integer.parseInt(obj[2].toString()));
				mess.setCreateTime(obj[3]== null ? new Date():(Timestamp)obj[3]);
				list.add(mess);
			}
		}
		//String hql = " from CommonMessage c where c.toUser.id = ? and c.pId is null and c.deleteFlag <> ? order by c.createTime desc ";
		//List<CommonMessage> list = receiverDao.find(hql, user_id,Constants.MESSAGE_DELETE_RECEIVE);
		return list;
	}
	
	
	

	@Override
	public Long save(CommonMessage comm) {
		// TODO Auto-generated method stub
		return (Long) receiverDao.save(comm);
	}

	@Override
	public void updateMutualFlag(Serializable id, Serializable id2,
			String string) {
		/*CommonMessage comm = findById((Long)id);
		comm.setMutualFlag(string);
		CommonMessage comm2 = findById((Long)id2);
		comm2.setMutualFlag(string);
		receiverDao.update(comm);
		receiverDao.update(comm2);*/
	}

	@Override
	public CommonMessage findById(Long id) {
		
		Criteria  c = receiverDao.getSession().createCriteria(CommonMessage.class);
		c.add(Restrictions.eq("id", id));
		List<CommonMessage> list = c.list();
		
		return list!=null&&list.size()>0 ? list.get(0) : null;
	}

	@Override
	public void updateMutualFlag(Long id, String string) {
		CommonMessage comm = findById(id);
		comm.setMutualFlag(string);
		receiverDao.update(comm);
	}

}
