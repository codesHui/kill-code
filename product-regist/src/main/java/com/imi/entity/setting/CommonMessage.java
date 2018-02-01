package com.imi.entity.setting;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.imi.base.BaseEntity;
import com.imi.entity.security.User;

/**
 * 通用消息
 * 
 *
 */

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_common_message")
public class CommonMessage extends BaseEntity {

	private static final long serialVersionUID = 2838500431499793719L;

	private String title, text;
	private Integer status; // 已发送 1  未发送0
	private Integer state;  // 已读1  未读0



	private User fromUser;  // 发送人
	private User  toUser;  // 接收人
	
	private int is_deleted_Sender; // 删除 1 未删除 0

	private int is_deleted_Receiver; // 删除 1 未删除 0
	
	private Long pId; 
	
	// 交互标示
	private String mutualFlag;
	
	// 当第一次交互后，将firstMutualFlag由 0 变为 1 
	private int firstMutualFlag;
	// 标识第一次是收件（1） 还是发件（2）
	private int receiveOrSend;
	// 标示 1/系统发送  0/非系统发送
	private int sendType;
	// 删除标示  1/发送方删除   2/接收方删除 
	private int deleteFlag;
	
	
	
	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getSendType() {
		return sendType;
	}

	public void setSendType(int sendType) {
		this.sendType = sendType;
	}

	public int getReceiveOrSend() {
		return receiveOrSend;
	}

	public void setReceiveOrSend(int receiveOrSend) {
		this.receiveOrSend = receiveOrSend;
	}

	public String getMutualFlag() {
		return mutualFlag;
	}

	public void setMutualFlag(String mutualFlag) {
		this.mutualFlag = mutualFlag;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fromUser_id")
	public User getFromUser() {
		return fromUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toUser_id")
	public User getToUser() {
		return toUser;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}


	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public int getIs_deleted_Sender() {
		return is_deleted_Sender;
	}

	public int getIs_deleted_Receiver() {
		return is_deleted_Receiver;
	}

	public void setIs_deleted_Sender(int is_deleted_Sender) {
		this.is_deleted_Sender = is_deleted_Sender;
	}

	public void setIs_deleted_Receiver(int is_deleted_Receiver) {
		this.is_deleted_Receiver = is_deleted_Receiver;
	}

	public int getFirstMutualFlag() {
		return firstMutualFlag;
	}

	public void setFirstMutualFlag(int firstMutualFlag) {
		this.firstMutualFlag = firstMutualFlag;
	}
	
	
	

}