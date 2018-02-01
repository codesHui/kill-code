package com.imi.entity.setting;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.imi.base.BaseEntity;

/**
 *注册规则。
 * 
 */
@Entity
@Table(name = "biz_regist_rule")
public class RegistRule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121597427009207813L;
	
	
	private MemberUser memberUser;
	private int serialNumber, year;

	private Date  startTime, endTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registeredCode", nullable = false)
	public MemberUser getMemberUser() {
		return memberUser;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public int getYear() {
		return year;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setMemberUser(MemberUser memberUser) {
		this.memberUser = memberUser;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}




}