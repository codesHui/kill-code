package com.imi.entity.reportPolicy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.entity.AbstractEntity;

/**
 * 保单上报的文件轨迹记录
 * @author Devon
 *
 */
@Entity
@Table(name = "biz_policy_file_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_policy_file_history")
public class ReportPolicyHistory extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String fileName;//上传的文件名
	private Date reportTime;//上报时间
	private String isSuccess;//是否成功
	private String remark;//描述
	private Date createdTime;//数据创建时间
	private Date modifiedTime;//数据修改时间
	private Long userId;//用户id
	
	@Column(name="file_name")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Column(name="report_time")
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	@Column(name="is_success")
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@Column(name="created_time")
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@Column(name="modified_time")
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
