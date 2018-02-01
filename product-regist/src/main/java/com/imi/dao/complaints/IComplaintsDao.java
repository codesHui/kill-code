package com.imi.dao.complaints;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.security.ComplaintsInfo;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Verifys;
import com.imi.model.product.ProductInfo;

public interface IComplaintsDao extends  IBaseDao<Complaints> {
	/*
	public List<Complaints> find(ComplaintsInfo info) ;
*/
	public void update(ComplaintsInfo info) throws Exception;
	
	public void findComplaints(ProductInfo info);
	
	public void findDisposeComplaints(ProductInfo info);

	public ProductInfo findProduct(String productNO);

	public Complaints findComplaintsById(String id);

}
