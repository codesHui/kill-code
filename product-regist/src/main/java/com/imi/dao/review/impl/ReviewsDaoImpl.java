package com.imi.dao.review.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.imi.base.impl.BaseDaoImpl;
import com.imi.base.model.reviews.ReviewsInfo;
import com.imi.dao.review.IReviewsDao;
import com.imi.entity.products.Reviews;

@Repository("reviewsDao")
public class ReviewsDaoImpl extends BaseDaoImpl<Reviews> implements IReviewsDao {
	private static final Logger logger = Logger.getLogger(ReviewsDaoImpl.class);

	@Override
	public List<Reviews> findReviews(ReviewsInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询Reviews数据...");
		String hql = "select r from Reviews r where r.product.status !=4";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		hql+=" order by r.id desc";
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}

	@Override
	public Long total(ReviewsInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("统计Reviews总数...");
		String hql = "select count(*) from Reviews r where r.product.status !=4";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(ReviewsInfo info, String hql,
			Map<String, Object> parameters) {
		if (info.getFlowCheck1()!=0) {
			hql += " and (r.flowCheck1 = :flowCheck1) ";
			parameters.put("flowCheck1", info.getFlowCheck1());
		}
		if (info.getFlowCheck2()!=0) {
			hql += " and (r.flowCheck2 = :flowCheck2) ";
			parameters.put("flowCheck2", info.getFlowCheck2());
		}
		if (info.getProduct().getProductType() != 0) {
			hql += " and (r.product.productType = :productType) ";
			parameters.put("productType", info.getProduct().getProductType());
		}
		if (!StringUtils.isEmpty(info.getProduct().getProductNO())) {
			hql += " and (r.product.productNO like :productNO) ";
			parameters.put("productNO", "%"+ info.getProduct().getProductNO() + "%");
		}
		if (!StringUtils.isEmpty(info.getProduct().getRegistPerson())) {
			hql += " and (r.product.registPerson like :registPerson) ";
			parameters.put("registPerson", "%"+ info.getProduct().getRegistPerson() + "%");
		}
		if (info.getProduct().getStatus()!=0) {
			hql += " and (r.status = :status) ";
			parameters.put("status", info.getProduct().getStatus());
		}
		if (!StringUtils.isEmpty(info.getProduct().getProductName())) {
			hql += " and (r.product.productName like :productName)";
			parameters.put("productName", "%"+ info.getProduct().getProductName() + "%");
		}
		if (!StringUtils.isEmpty(info.getProduct().getRiskType())) {
			hql += " and (r.product.riskType = :riskType) ";
			parameters.put("riskType", info.getProduct().getRiskType());
		}
		if (!StringUtils.isEmpty(info.getProduct().getRiskName())) {
			hql += " and (r.product.riskName = :riskName) ";
			parameters.put("riskName", info.getProduct().getRiskName());
		}
		if (!StringUtils.isEmpty(info.getRole())) {
			hql += " and (r.role = :role) ";
			parameters.put("role", info.getRole());
			if(!StringUtils.isEmpty(info.getHasReviews())&&!info.getHasReviews().equals("0")){
				//未复查
				if(info.getHasReviews().equals("1")){
					if("admin1".equals(info.getRole().getCode())){
						hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{0});
					}else if("admin2".equals(info.getRole().getCode())){
						hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{0,2});
					}else if("admin3".equals(info.getRole().getCode())){
						 hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{0,3,5});
					}
					
				}
				//已复查
				if(info.getHasReviews().equals("2")){
					if("admin1".equals(info.getRole().getCode())){
						hql+=" and status in (:allstatus)";
						 System.err.println("status=="+info.getHasReviews()+"/"+info.getRole().getCode());
						 parameters.put("allstatus",new Integer[]{1});
					}else if("admin2".equals(info.getRole().getCode())){
						hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{1});
					}else if("admin3".equals(info.getRole().getCode())){
						 hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{1});
					}
					
				}
			}else if(StringUtils.isEmpty(info.getHasReviews())||info.getHasReviews().equals("0")){
				
				
				if(("2").equals(info.getHasReviews())){
					if("admin1".equals(info.getRole().getCode())){
						hql+=" and status in (:allstatus)";
						 System.err.println("status=="+info.getHasReviews()+"/"+info.getRole().getCode());
						 parameters.put("allstatus",new Integer[]{0,1});
					}else if("admin2".equals(info.getRole().getCode())){
						 hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{0,1,2});
					}else if("admin3".equals(info.getRole().getCode())){
						hql+=" and status in (:allstatus)";
						 parameters.put("allstatus",new Integer[]{0,1,3,5});
					}
					
				}
		    }
		 }
		return hql;
	}
}
