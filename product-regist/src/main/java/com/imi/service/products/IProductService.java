package com.imi.service.products;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.imi.entity.products.Product;
import com.imi.entity.products.ProductHistroy;
import com.imi.entity.setting.Attachment;
import com.imi.entity.setting.MemberUser;
import com.imi.model.product.ProductInfo;
/**
 * 产品服务接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IProductService {
	
	/**
	 * 查询产品。
	 * @param info
	 * 产品信息。
	 * @return
	 * 产品数据。
	 */
	List<Product> findProducts(ProductInfo info, Date start, Date end,Date find);
	
	Product findProductById(Long id);
	
	/**
	 * 更新产品。
	 * @param info
	 * 产品信息。
	 * @return
	 * 产品数据。
	 */
	String insert(ProductInfo info,List<Attachment> attList);
	
	/**
	 * 删除产品。
	 * @param productId
	 */
	void deleteProduct(String userId);
	
	/**
	 * 统计产品数量。
	 * @param info
	 * @return
	 * 总数。
	 */
	public Long totalNums(ProductInfo info);
	
	public void registProduct(ProductInfo info,MemberUser user,List<Attachment> attList);
	

	Product findProductByProductNO(String productNO);

	void saveProduct(Product product);
	
	

	/**
	 * @param type 1 :产品注册 ，2:产品复查，3:产品核查,4:产品投诉 5.产品注销
	 * @param productNO 产品编号
	 * @param content 提交内容
	 * @param actionCode 动作代码
	 * @param actionName 动作名称 （自动注销 审核通过 复查通过）
	 * @param roleCode 角色
	 * @param userId  用户id
	 * @param fkId  操作表Id
	 * @param status 是否生效
	 * @param attachmentIds 附件列表 
	 */
	public 	void insertProductLog(int type ,String actionCode, String actionName,String roleCode,Long userId ,String productNO,Long fkId, String content,int status,Collection<Long> attachmentIds);
	
	/**
	 * 
	 * @param productHistory
	 */
	public 	void insertProductLog(ProductHistroy productHistory);
	
	
	
	
	public List<ProductHistroy> findallProductLog(ProductHistroy productHistory);

	
}