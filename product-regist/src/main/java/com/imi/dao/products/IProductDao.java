package com.imi.dao.products;

import java.util.Date;
import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.entity.products.Product;
import com.imi.model.product.ProductInfo;


/**
 * 产品数据接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IProductDao extends IBaseDao<Product> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Product> findProducts(ProductInfo info, Date start,Date end,Date find);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ProductInfo info);
	/**
	 * 根据账号查找产品。
	 * @param account
	 * 账号。
	 * @return
	 * 产品。
	 */
	 Product findByAccount(String account);
 
	 Product findById(Long id);
 
	 /**
	 * 新增产品。
	 * @param account
	 * 账号。
	 * @return
	 * 产品。
	 */
	 public Long insert(Product data);
 
}