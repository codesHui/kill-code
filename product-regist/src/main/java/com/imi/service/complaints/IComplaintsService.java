package com.imi.service.complaints;

import java.util.List;

import com.imi.base.model.security.ComplaintsInfo;
import com.imi.base.model.security.VerifysInfo;
import com.imi.base.service.IBaseDataService;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Verifys;
import com.imi.model.product.ProductInfo;

public interface IComplaintsService extends IBaseDataService<ComplaintsInfo>  {
	
	public ComplaintsInfo getProductSkipComplaints(Long id);
	/*
	public List<Complaints> find(ComplaintsInfo info);*/

	public void updateComplain(ComplaintsInfo info) throws Exception;
	
	public void findComplaints(ProductInfo productInfo);
	
	public void findDisposeComplaints(ProductInfo productInfo);
	
	public ProductInfo findProduct(String productNO)throws Exception;

	public Complaints findComplaintsById(String id);
	
	public void save(ComplaintsInfo info) throws Exception;
}
