package com.imi.service.setting;

import java.io.Serializable;
import java.util.List;

import com.imi.entity.setting.CommonMessage;

public interface ReceiverService {

	
	
	/**
     * 查询用户的未读的站内信数量 
     * @param receiveUser
     * @return
     */
    Integer selectNoReadCount(Long receiveUser);
    
    /**
     * 查询用户所有站内信总数
     * @param receiveUser
     * @return

     */
    Integer selectAllCount(Long receiveUser);
    
   
    /**
     * 更新消息状态为已读
     * @param record
     * @return
     */
    int updateReadPrimaryKey(CommonMessage record);
    /**
     * 更具IDS删除记录
     * @param ids
     * @return

     */
    int deleteMessage(String ids,String type);
    
    int deleteMessage(String[] ids);
    
    List<CommonMessage> getReceiveList(Long user_id);
    int getMessageTotal(Long user_id);

    CommonMessage getMessById(Long id);
    
    Serializable  insertMess(String title,String text,Long from_id,Long to_id)  throws Exception;
    
    void updateReadStateById(Long id);
    
	Integer findReadYes(Long current_user_id);

	void insertMessageMutual_shouxin(CommonMessage comm)  throws Exception;

	void insertMessageMutual_faxin(CommonMessage comm)  throws Exception;
	
	List<CommonMessage> findReceiverChatRecord(CommonMessage comm);

	Long save(CommonMessage comm);
	/**
	 * 修改交互标示
	 * @param id 信息ID1
	 * @param id2 信息ID1
	 * @param string 标示号
	 */
	void updateMutualFlag(Serializable id, Serializable id2, String string);
    
	CommonMessage findById(Long id);
	
	public List<CommonMessage> findSendChatRecord(CommonMessage comm);
	
	public List<CommonMessage> findSendInfo(Long user_id);

	void updateMutualFlag(Long id, String string);
    
	
}