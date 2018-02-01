package com.imi.dao.products;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.products.ProductFile;
import com.imi.entity.setting.Attachment;
import com.imi.model.product.ProductFileInfo;

public interface IProductFileDao extends IBaseDao<ProductFile>  {
	
	
	/**
	 * 根据产品编号、类别、以及文件类型
	 * @param productNO
	 * @param type
	 * @param extension
	 * @return
	 */
	public List<ProductFile> findAllbyProductNOAndType(String productNO,int type,String extension );
	/**
	 * 根据产品编号以及类别
	 * @param productNO
	 * @param type
	 * @return
	 */
	public List<ProductFile> findAllbyProductNOAndType(String productNO,int type);
	/**
	 * 根据产品编号
	 * @param productNO
	 * @param type
	 * @return
	 */
	public List<ProductFile> findAllByProductNO(String productNO);
	/**
	 * 根据产品信息
	 * @param productNO
	 * @param type
	 * @return
	 */
	public List<ProductFile> findAllByProductInfo(ProductFileInfo productInfo);
	
	public void updateBySql(List<Attachment> attList, String productNO);
	
}
