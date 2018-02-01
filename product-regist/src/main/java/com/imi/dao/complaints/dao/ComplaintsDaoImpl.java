package com.imi.dao.complaints.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.security.ComplaintsInfo;
import com.imi.dao.complaints.IComplaintsDao;
import com.imi.dao.products.IProductDao;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.model.product.ProductInfo;
import com.imi.support.Constants;
@Repository
public class ComplaintsDaoImpl extends BaseDaoImpl<Complaints> implements IComplaintsDao {
	
	
	
	@Resource
	private IProductDao productDao;
	
	
	@Override
	public void findComplaints(ProductInfo info) {
		
		List<Complaints> listCount = this.find(sql_content());
		
		info.setTotal(listCount == null ? 0 : listCount.size());
		String hql = this.sql_content() +  this.sql_condition(info);
		List<Complaints> cList = this.find(hql, null, info.getPage(), info.getRows());
		info.setcList(cList);
		
	}
	
	
	/**
	 * 查询sql内容
	 * @return
	 */
	private String sql_content(){
		StringBuffer sql = new StringBuffer();
		sql.append("select c from Complaints c left join c.product p left join p.user m where 1=1 ");
		return sql.toString();
	}
	
	/**
	 * 查询SQL条件
	 * @param info
	 * @return
	 */
	private String sql_condition(ProductInfo info){
		
		StringBuffer sql = new StringBuffer();
		
		//产品类型	
		if(!StringUtils.isEmpty(info.getProductType()+"") && info.getProductType() != 0){
			sql.append(" and p.productType  = '").append(info.getProductType()).append("' ");
		}
		//注册号
		if(!StringUtils.isEmpty(info.getProductNO())){
			sql.append(" and p.productNO LIKE '%").append(trim(info.getProductNO())).append("%' ");
		}
		//注册人
		if(!StringUtils.isEmpty(info.getOffreason())){
			sql.append(" and m.registeredPerson LIKE '%").append(trim(info.getOffreason())).append("%' ");
		}
		//投诉处理状态
		if(!StringUtils.isEmpty(info.getStatus()+"") && info.getStatus() !=0 ){
			sql.append(" and c.status = ").append(info.getStatus());
		}
		//产品名称
		if(!StringUtils.isEmpty(info.getProductName())){
			sql.append(" and p.productName like '%").append(trim(info.getProductName())).append("%' ");
		}
		
		sql.append(" order by c.createTime desc ");
		
		return sql.toString();
	}
	
	public String trim(String string){
		return StringUtils.trim(string);
	}
	
	

	/*@Override
	public List<Complaints> find(ComplaintsInfo info) {
		String hql = " from Complaints v where v.product.productNO =  ? and v.status = ? " ;
		List<Complaints> list = null;
		try {
			list = super.find(hql, info.getProductNO(),Constants.COMPLAINS_STATUS_YES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}*/

	@Override
	public void update(ComplaintsInfo info) throws Exception {
		List<Complaints> list = super.find("from Complaints c where c.id = ?", info.getComplaints().getId());
		if(list == null || list.size() ==0){
			throw new Exception("更新投诉对象不存在");
		}
		Complaints com = list.get(0);
		com.setResultReason(info.getComplaints().getResultReason());
		com.setResult(info.getComplaints().getResult());
		com.setResultDate(new Date());
		com.setStatus(Constants.COMPLAINS_STATUS_YES);
		// TODO XUGANG 处理人
//		com.setResultMan("");
		super.update(com);
	}







	@Override
	public ProductInfo findProduct(String productNO) {
		String hql = " from Product v where v.productNO = ? " ;
		List<Product> list = null;
		ProductInfo info = new ProductInfo();
		try {
			list = super.find(hql, productNO);
			if(list == null || list.size()==0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		info.setProduct(list.get(0));
		return info;
	}







	@Override
	public Complaints findComplaintsById(String id) {
		String hql = " from Complaints v where v.id = ? " ;
		List<Complaints> list = null;
		try {
			list = super.find(hql, Long.parseLong(id));
			if(list == null || list.size()==0){
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.get(0);
	}
	
	
	
	/*************************管理员页面 只查询已处理的投书数据************************/
	
	@Override
	public void findDisposeComplaints(ProductInfo info) {
		StringBuffer sql = new StringBuffer();
		sql.append("select c from Complaints c left join c.product p left join p.user m where 1=1 ");
		sql.append("  and c.status = ").append(Constants.COMPLAINS_STATUS_YES);
		List<Complaints> listCount = this.find(sql.toString());
		
		//产品类型	
		if(!StringUtils.isEmpty(info.getProductType()+"") && info.getProductType() != 0){
			sql.append(" and p.productType  = '").append(info.getProductType()).append("' ");
		}
		//注册号
		if(!StringUtils.isEmpty(info.getProductNO())){
			sql.append(" and p.productNO LIKE '%").append(trim(info.getProductNO())).append("%' ");
		}
		//注册人
		if(!StringUtils.isEmpty(info.getOffreason())){
			sql.append(" and m.registeredPerson LIKE '%").append(trim(info.getOffreason())).append("%' ");
		}
		//投诉处理状态
		/*if(!StringUtils.isEmpty(info.getStatus()+"") && info.getStatus() !=0 ){
			sql.append(" and c.status = ").append(info.getStatus());
		}*/
		//产品名称
		if(!StringUtils.isEmpty(info.getProductName())){
			sql.append(" and p.productName like '%").append(trim(info.getProductName())).append("%' ");
		}
		sql.append(" order by c.createTime desc ");
		
		info.setTotal(listCount == null ? 0 : listCount.size());
		List<Complaints> cList = this.find(sql.toString(), null, info.getPage(), info.getRows());
		info.setcList(cList);
		
	}
	
	
	
	
	

}
