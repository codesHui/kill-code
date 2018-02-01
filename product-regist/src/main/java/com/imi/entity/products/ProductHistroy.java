package com.imi.entity.products;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.imi.base.BaseEntity;
import com.imi.entity.setting.Attachment;


/**
 * 产品操作日志 相关信息
 * @author josh
 *
 */
@Entity
@Table(name = "biz_product_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "biz_product_history")
public class ProductHistroy  extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer type; // 1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉
	
	private String actionCode; //操作代码 与一致 1234
	private String  actionName;//操作动作 复查通过 复查失败 上报上级  核查
	private String roleCode;//当前角色

	private String operPerson;//提交人

	private String productNO;// 产品编号
	private Integer status;//状态
	private  Long fkId;//关联表主键
	private String content;// 提交内容

	
	private List<Attachment> attachment; //附件列表
	
	@ManyToMany
	@JoinTable(name = "sys_productHistory_file", joinColumns = @JoinColumn(name = "productHistory_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "attachment_id", referencedColumnName = "id"))
	public List<Attachment> getAttachment() {
		return attachment;
	}

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	@Column(name = "productNO")
	public String getProductNO() {
		return productNO;
	}
	@Column(name = "operPerson")
	public String getOperPerson() {
		return operPerson;
	}


	public void setProductNO(String productNO) {
		this.productNO = productNO;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}
	@Column(name = "actionName")
	public String getActionName() {
		return actionName;
	}

	
	@Column(name = "roleCode")
	public String getRoleCode() {
		return roleCode;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	
	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getFkId() {
		return fkId;
	}

	public void setFkId(Long fkId) {
		this.fkId = fkId;
	}


	
	public void setAttachment(List<Attachment> attachment) {
		this.attachment = attachment;
	}


	public String getActionCode() {
		return actionCode;
	}


	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	

	
	

	


}
