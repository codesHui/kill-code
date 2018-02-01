package com.imi.dao.products.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softvan.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.dao.products.IProductDao;
import com.imi.entity.products.Product;
import com.imi.model.product.ProductInfo;
/**
 * 产品数据接口实现类。
 */
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao {
	private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.softvan.netplatform.dao.admin.IProductDao#findProducts(com.softvan.netplatform.model.admin.ProductInfo)
	 */
	@Override
	public List<Product> findProducts(ProductInfo info, Date start,Date end,Date find) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "select p from Product p where 1=1 "; 
		Map<String, Object> parameters = new HashMap<String, Object>();
		//根据日期查询
		if(!StringUtils.isEmpty(start)){
			parameters.put("start",start);
		}
		if(!StringUtils.isEmpty(end)){
			parameters.put("end",end);
		}
		if(!StringUtils.isEmpty(find)){
			parameters.put("find",find);
		}
		if(!StringUtils.isEmpty(info.getProductName())){
			parameters.put("productName", "%"+info.getProductName()+"%");
		}
		if(!StringUtils.isEmpty(info.getProductNO())){
			parameters.put("productNO", "%"+info.getProductNO()+"%");
		}
		if(info.getProductType() != 0){
			parameters.put("productType", info.getProductType());
		}
		if(!StringUtils.isEmpty(info.getRegistPerson())){
			parameters.put("registPerson", "%"+info.getRegistPerson()+"%");
		}
		if(info.getStatus() != 0){
			parameters.put("status", info.getStatus());
		}
		if(info.getRiskType() != null && info.getRiskType() != ""){
			parameters.put("riskType", info.getRiskType());
		}
		if(info.getRiskName() != null && info.getRiskName() != ""){
			parameters.put("riskName", info.getRiskName());
		}
		if(info.getUser() != null){
			if(!StringUtils.isEmpty(info.getUser().getRegisteredCode())){
				parameters.put("registeredCode", info.getUser().getRegisteredCode());
			}
		}
		if(!StringUtils.isEmpty(info.getKeywords())){
			parameters.put("keywords", "%"+info.getKeywords()+"%");
		}
		
		hql = this.addWhere(info, hql, parameters,start,end,find);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.softvan.netplatform.dao.admin.IProductDao#total(com.softvan.netplatform.model.admin.ProductInfo)
	 */
	@Override
	public Long total(ProductInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Product p where 1=1";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(info.getProductName())){
			parameters.put("productName", "%"+info.getProductName()+"%");
		}
		if(!StringUtils.isEmpty(info.getProductNO())){
			parameters.put("productNO", "%"+info.getProductNO()+"%");
		}
		if(info.getProductType() != 0){
			parameters.put("productType", info.getProductType());
		}
		if(!StringUtils.isEmpty(info.getRegistPerson())){
			parameters.put("registPerson", "%"+info.getRegistPerson()+"%");
		}
		if(info.getStatus() != 0){
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getRiskType())){
			parameters.put("riskType", info.getRiskType());
		}
		if(!StringUtils.isEmpty(info.getRiskName())){
			parameters.put("riskName", info.getRiskName());
		}
		if(info.getUser() != null){
			if(!StringUtils.isEmpty(info.getUser().getRegisteredCode())){
				parameters.put("registeredCode", info.getUser().getRegisteredCode());
			}
		}
		if(!StringUtils.isEmpty(info.getKeywords())){
			parameters.put("keywords", "%"+info.getKeywords()+"%");
		}
		hql = this.addWhere(info, hql, parameters,null,null,null);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(ProductInfo info, String hql, Map<String, Object> parameters,Date start,Date end,Date find){

		//注册时间
		if(!StringUtils.isEmpty(start)&&!StringUtils.isEmpty(end)){
			hql+="and Date(p.registTime) between :start and :end ";
		}
		if(!StringUtils.isEmpty(find)){
			hql+=" and Date(p.registTime) = :find ";
		}
		//产品类型
		if(!StringUtils.isEmpty(info.getProductType()) && info.getProductType() != 0){
			hql += " and p.productType = :productType ";
		}
		//注册号
		if(!StringUtils.isEmpty(info.getProductNO())){
			hql += " and p.productNO LIKE :productNO ";
		}
		//注册人
		if(!StringUtils.isEmpty(info.getRegistPerson())){
			hql += " and p.registPerson LIKE :registPerson ";
		}
		//状态
		if(!StringUtils.isEmpty(info.getStatus()) && info.getStatus() !=0  && StringUtils.isEmpty(info.getTitle())){
			hql += " and p.status = :status ";
		}
		//状态
		if(info.getStatus() == 4 && !StringUtils.isEmpty(info.getTitle())){
			hql += " and p.status != :status ";
		}
		//产品名称
		if(!StringUtils.isEmpty(info.getProductName())){
			hql += " and p.productName LIKE :productName " ;
		}
		//险类
		if(!StringUtils.isEmpty(info.getRiskType())){
			hql += " and p.riskType = :riskType ";
		}
		//险种
		if(!StringUtils.isEmpty(info.getRiskName())){
			hql += " and p.riskName = :riskName  ";
		}
		//险种
		if(info.getUser() != null){
			if(!StringUtils.isEmpty(info.getUser().getRegisteredCode())){
				hql += " and p.user.registeredCode = :registeredCode  ";
			}
		}
		//首页模糊查询
		if(!StringUtils.isEmpty(info.getKeywords())){
			hql += " and (p.productName LIKE :keywords or p.productNO LIKE :keywords or p.registPerson LIKE :keywords or p.user.companyName  LIKE :keywords or   p.user.companyShortName  LIKE :keywords)";
		}
		hql += " order by p.createTime desc";
		return hql;
	}
	/*
	 * 根据账号查找产品。
	 * @see com.softvan.netplatform.dao.admin.IProductDao#findByAccount(java.lang.String)
	 */
	@Override
	public Product findByAccount(String productNO) {
		if(logger.isDebugEnabled()) logger.debug(String.format("根据账号查询产品：%s", productNO));
		if(StringUtils.isEmpty(productNO)) return null;
		final String hql = "from Product u where u.productNO = :productNO";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productNO", productNO);
		List<Product> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 重载删除数据。
	 * @see com.softvan.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Product data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) return;
		int count = 0;
	/*	if(data.getAgencies() != null && (count = data.getAgencies().size()) > 0){
			throw new RuntimeException(String.format("产品［%1$s］关联［%2$d］机构，暂不能删除！", data.getName(), count));
		}
		if(data.getTopics() != null && (count = data.getTopics().size()) > 0){
			throw new RuntimeException(String.format("产品［%1$s］关联［%2$d］教师答疑主题，暂不能删除！", data.getName(), count));
		}
		if(data.getDetails() != null && (count = data.getDetails().size()) > 0){
			throw new RuntimeException(String.format("产品［%1$s］关联［%2$d］教师答疑回复，暂不能删除！", data.getName(), count));
		}
		if(data.getOrders() != null && (count = data.getOrders().size()) > 0){
			throw new RuntimeException(String.format("产品［%1$s］关联［%2$d］订单，暂不能删除！", data.getName(), count));
		}*/
		super.delete(data);
	}
	/*
	 * 新增数据。
	 * @see com.softvan.netplatform.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public Long insert(Product data) {
		if(logger.isDebugEnabled()) logger.debug("重载删除数据...");
		if(data == null) {
			return null;
		}
		return (Long)this.save(data);
	}

	@Override
	public Product findById(Long id) {
		// TODO Auto-generated method stub
		return super.load(Product.class, id);
	}
}