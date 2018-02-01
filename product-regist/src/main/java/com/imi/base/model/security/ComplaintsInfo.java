package com.imi.base.model.security;

import java.util.List;

import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.entity.setting.MemberUser;
import com.softvan.model.Paging;

public class ComplaintsInfo extends Paging {
	
	private int total;
	
	private Complaints complaints;
	
	private String productNO;
	
	private Product product;
	private MemberUser user;
	
	private List<ComplaintsInfo> list;
	
	private int currentRowNo;
	
	private Long current_user_id;
	
	
	

	
	public Long getCurrent_user_id() {
		return current_user_id;
	}

	public void setCurrent_user_id(Long current_user_id) {
		this.current_user_id = current_user_id;
	}

	public int getCurrentRowNo() {
		return currentRowNo;
	}

	public void setCurrentRowNo(int currentRowNo) {
		this.currentRowNo = currentRowNo;
	}

	public MemberUser getUser() {
		return user;
	}

	public void setUser(MemberUser user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Complaints getComplaints() {
		return complaints;
	}

	public void setComplaints(Complaints complaints) {
		this.complaints = complaints;
	}

	public String getProductNO() {
		return productNO;
	}

	public void setProductNO(String productNO) {
		this.productNO = productNO;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ComplaintsInfo> getList() {
		return list;
	}

	public void setList(List<ComplaintsInfo> list) {
		this.list = list;
	}
	
	
	
	

}
