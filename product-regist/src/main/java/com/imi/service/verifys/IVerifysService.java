package com.imi.service.verifys;

import java.util.List;

import com.imi.base.model.security.VerifysInfo;
import com.imi.base.service.IBaseDataService;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.entity.setting.Attachment;
import com.imi.model.product.ProductInfo;


public interface IVerifysService extends IBaseDataService<VerifysInfo>  {
	
	
	Verifys get(Long id);
	
	void save(Verifys vf);
	
	List<Verifys> find(VerifysInfo v);
	
	void update(Verifys v,Attachment attachment) throws Exception;

	void updateComplain(VerifysInfo info)  throws Exception;
	void updateComplain4(VerifysInfo info)  throws Exception;

	void findProducts(ProductInfo productInfo);
	/**
	 * 查询管理员发起核查的数据
	 * @param productInfo
	 * @return
	 */
	void findUserProducts(ProductInfo productInfo);

	void updateComplainByUser3(VerifysInfo info)throws Exception;
	
	String updateComplainByUser4(VerifysInfo info)throws Exception;

	void updateVerifysStatus();
	void findProductsCirs(ProductInfo productInfo);

}
