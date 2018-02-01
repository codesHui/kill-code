package com.imi.dao.verifys.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.security.VerifysInfo;
import com.imi.dao.products.IProductDao;
import com.imi.dao.verifys.IVerifysDao;
import com.imi.entity.products.Complaints;
import com.imi.entity.products.Product;
import com.imi.entity.products.Verifys;
import com.imi.model.product.ProductInfo;
import com.imi.support.Constants;

@Repository
public class VerifysDaoImpl extends BaseDaoImpl<Verifys> implements IVerifysDao {
	
	@Resource
	private IProductDao productDao;
	
	@Override
	public void findProducts(ProductInfo info) {
		List<Verifys> listCount = this.find(sql_content());
		info.setTotal(listCount == null ? 0 : listCount.size());
		String hql = this.sql_content() + this.sql_condition(info);
		List<Verifys> v = this.find(hql, null, info.getPage(), info.getRows());
		info.setvList(v);
	}
	
	/**
	 * 查询SQ的结果
	 * @return
	 */
	private String sql_content(){
		
		String hql = " select v from Verifys v left join v.product p "
				+ "left join p.user m left join m.user u where 1=1 and v.status in ('1','3','5') ";
		
		return hql;
		
	}
	
	/**
	 * 查询SQL的条件
	 * @param info
	 * @return
	 */
	private String sql_condition(ProductInfo info){
		
		StringBuffer sql = new StringBuffer();
		
		//产品类型	
		if(!StringUtils.isEmpty(info.getProductType()) && info.getProductType() != 0){
			sql.append(" and p.productType  = '").append(info.getProductType()).append("' ");
		}
		//注册号
		if(!StringUtils.isEmpty(info.getProductNO())){
			sql.append(" and p.productNO LIKE '%").append(info.getProductNO()).append("%' ");
		}
		//注册人
		if(!StringUtils.isEmpty(info.getOffreason())){
			sql.append(" and m.registeredPerson LIKE '%").append(info.getOffreason()).append("%' ");
		}
		//状态
		if(!StringUtils.isEmpty(info.getStatus()) && info.getStatus() !=0 ){
			sql.append(" and v.status = ").append(info.getStatus());
		}
		//产品名称
		if(!StringUtils.isEmpty(info.getProductName())){
			sql.append(" and p.productName LIKE '%").append(info.getProductName()).append("%' ");
		}
		//险类
		if(!StringUtils.isEmpty(info.getRiskType())){
			sql.append(" and p.riskType =  '").append(info.getRiskType()).append("' ");
		}
		//险种
		if(!StringUtils.isEmpty(info.getRiskName())){
			sql.append(" and p.riskName =  '").append(info.getRiskName()).append("' ");
		}
		//注册人代码
		if(info.getUser() != null){
			if(!StringUtils.isEmpty(info.getUser().getRegisteredCode())){
				sql.append(" and m.registeredCode like '%").append(info.getUser().getRegisteredCode()).append("%' ");
			}
		}
		//险种
		if(!StringUtils.isEmpty(info.getKeywords())){
			sql.append(" and p.productName like  '%").append(info.getKeywords()).append("%' ");
		}
		sql.append(" order by v.createTime desc ");
		return sql.toString();
	}

	public String trim(String string){
		return StringUtils.trimWhitespace("");
	}
	
	
	@Override
	public List<Verifys> find(VerifysInfo info) {
		String hql = " from Verifys v where v.product.productNO = ? and v.id = ?" ;
		List<Verifys> list = null;
		try {
			list = super.find(hql, info.getProductNO(),info.getVerifys().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateComplain(VerifysInfo info) throws Exception {
		List<Verifys> list = super.find("from Verifys c where c.id = ?", info.getVerifys().getId());
		if(list == null || list.size() ==0){
			throw new Exception("更新核查对象不存在");
		}
		Verifys com = list.get(0);
		com.setResultReason(info.getVerifys().getResultReason());
		com.setResult(info.getVerifys().getResult());
		com.setResultDate(new Date());
		com.setCreateTime(new Date());
		com.setStatus(Constants.VERIFYS_STATUS_2);
		// TODO XUGANG 处理人
//		com.setResultMan("");
		super.update(com);
	}
	
	
	
	/*************************** user ************************************/
	@Override
	public void findUserProducts(ProductInfo info) {
		List<Verifys> listCount = this.find(sql_contentByUser(info));
		info.setTotal(listCount == null ? 0 : listCount.size());
		String hql = this.sql_contentByUser(info) + this.sql_conditionByUser(info);
		List<Verifys> v = this.find(hql, null, info.getPage(), info.getRows());
		info.setvList(v);
		
	}
	
	
	
	private String sql_contentByUser(ProductInfo info){
		String hql = " select v from Verifys v left join v.product p left "
				+ "join p.user m left join m.user u where v.status in ('2','4','5') and u.id =  "+info.getCurrentUserId();
		
		return hql;
		
	}
	
	private String sql_conditionByUser(ProductInfo info){
		
		StringBuffer sql = new StringBuffer();
		
		//产品类型	
		if(!StringUtils.isEmpty(info.getProductType()) && info.getProductType() != 0){
			sql.append(" and p.productType  = '").append(info.getProductType()).append("' ");
		}
		//注册号
		if(!StringUtils.isEmpty(info.getProductNO())){
			sql.append(" and p.productNO LIKE '%").append(trim(info.getProductNO())).append("%' ");
		}
		//注册人
		if(!StringUtils.isEmpty(info.getOffreason())){
			sql.append(" and p.user.user.name LIKE '%").append(trim(info.getOffreason())).append("%' ");
		}
		//状态
		if(!StringUtils.isEmpty(info.getStatus()) && info.getStatus() !=0 ){
			sql.append(" and v.status = ").append(info.getStatus());
		}
		//产品名称
		if(!StringUtils.isEmpty(info.getProductName())){
			sql.append(" and p.productName like '%").append(trim(info.getProductName())).append("%' ");
		}
		//险类
		if(!StringUtils.isEmpty(info.getRiskType())){
			sql.append(" and p.riskType =  '").append(info.getRiskType()).append("' ");
		}
		//险种
		if(!StringUtils.isEmpty(info.getRiskName())){
			sql.append(" and p.riskName =  '").append(info.getRiskName()).append("' ");
		}
		//注册人代码
		if(info.getUser() != null){
			if(!StringUtils.isEmpty(info.getUser().getRegisteredCode())){
				sql.append(" and m.registeredCode like  '%").append(trim(info.getUser().getRegisteredCode())).append("%' ");
			}
		}
		sql.append(" order by v.createTime desc ");
		/*
		if(!StringUtils.isEmpty(info.getKeywords())){
			sql.append(" and p.productName like  '%").append(info.getKeywords()).append("%' ");
		}*/
		/*if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += " order by u." + info.getSort() + " " + info.getOrder();
		}*/
		return sql.toString();
	}
	
	
	/***********************Cirs 页面 只查询已核查数据***********************/
	@Override
	public void findProductsCirs(ProductInfo info) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select v from Verifys v left join v.product p ");
		hql.append(" left join p.user m left join m.user u where 1=1 and v.status =").append(Constants.VERIFYS_STATUS_5);
		List<Verifys> listCount = this.find(hql.toString());
		info.setTotal(listCount == null ? 0 : listCount.size());

		//产品类型	
		if(!StringUtils.isEmpty(info.getProductType()) && info.getProductType() != 0){
			hql.append(" and p.productType  = '").append(info.getProductType()).append("' ");
		}
		//注册号
		if(!StringUtils.isEmpty(info.getProductNO())){
			hql.append(" and p.productNO LIKE '%").append(trim(info.getProductNO())).append("%' ");
		}
		//注册人
		if(!StringUtils.isEmpty(info.getOffreason())){
			hql.append(" and m.registeredPerson LIKE '%").append(trim(info.getOffreason())).append("%' ");
		}/*
		//状态
		if(!StringUtils.isEmpty(info.getStatus()) && info.getStatus() !=0 ){
			hql.append(" and v.status = ").append(info.getStatus());
		}*/
		//产品名称
		if(!StringUtils.isEmpty(info.getProductName())){
			hql.append(" and p.productName like '%").append(trim(info.getProductName())).append("%' ");
		}
		//险类
		if(!StringUtils.isEmpty(info.getRiskType())){
			hql.append(" and p.riskType =  '").append(info.getRiskType()).append("' ");
		}
		//险种
		if(!StringUtils.isEmpty(info.getRiskName())){
			hql.append(" and p.riskName =  '").append(info.getRiskName()).append("' ");
		}
		//注册人代码
		if(info.getUser() != null){
			if(!StringUtils.isEmpty(info.getUser().getRegisteredCode())){
				hql.append(" and m.registeredCode like '%").append(info.getUser().getRegisteredCode()).append("%' ");
			}
		}
		//险种
		if(!StringUtils.isEmpty(info.getKeywords())){
			hql.append(" and p.productName like  '%").append(info.getKeywords()).append("%' ");
		}
		hql.append(" order by v.createTime desc ");
		List<Verifys> v = this.find(hql.toString(), null, info.getPage(), info.getRows());
		info.setvList(v);
	}
	
	
	
	

	
}
