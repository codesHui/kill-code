package com.imi.entity.setting;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.imi.base.BaseEntity;
import com.imi.base.entity.Orderable;

/**
 * 字典实体类
 *
 * @date 2014/5/6
 */
@Entity
@Table(name = "sys_dic")
public class Dictionary extends BaseEntity implements Orderable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//类别 字典项 字典值 字典描述  备注
	private String dicType,dicKey,dicValue,dicDesc,remark;
	//深度 排序
    private int level,orderNo;

    private Set<Dictionary> children = new HashSet<Dictionary>();


    private Dictionary parent;

    private Boolean isBusinessData = true;

    private Boolean isDeleted = false;


	@Column(name="dic_type")
	public String getDicType() {
		return dicType;
	}
    @Column(name="dic_key")
	public String getDicKey() {
		return dicKey;
	}
    @Column(name="dic_value")
	public String getDicValue() {
		return dicValue;
	}
    @Column(name="dic_desc")
	public String getDicDesc() {
		return dicDesc;
	}
    @Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public int getLevel() {
		return level;
	}

	public int getOrderNo() {
		return orderNo;
	}

	@OneToMany(mappedBy = "parent")
	@Cascade(CascadeType.DELETE)
	public Set<Dictionary> getChildren() {
		return children;
	}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @NotFound(action = NotFoundAction.IGNORE)
	public Dictionary getParent() {
		return parent;
	}
    @Column(name="is_businessDate")
	public Boolean getIsBusinessData() {
		return isBusinessData;
	}
    @Column(name="is_deleted")
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public void setDicDesc(String dicDesc) {
		this.dicDesc = dicDesc;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public void setChildren(Set<Dictionary> children) {
		this.children = children;
	}

	public void setParent(Dictionary parent) {
		this.parent = parent;
	}

	public void setIsBusinessData(Boolean isBusinessData) {
		this.isBusinessData = isBusinessData;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
    	@Override
	public int compareTo(Orderable o) {
		return (int) (getOrderNo() - o.getOrderNo());
	}
}
