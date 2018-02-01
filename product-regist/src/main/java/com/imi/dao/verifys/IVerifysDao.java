
package com.imi.dao.verifys;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.security.VerifysInfo;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.model.product.ProductInfo;


public interface IVerifysDao extends IBaseDao<Verifys> {

	public List<Verifys> find(VerifysInfo info);

	public void updateComplain(VerifysInfo info) throws Exception;

	public void findProducts(ProductInfo productInfo);
	
	public void findUserProducts(ProductInfo productInfo);
	
	public void findProductsCirs(ProductInfo productInfo);
	
	

}

