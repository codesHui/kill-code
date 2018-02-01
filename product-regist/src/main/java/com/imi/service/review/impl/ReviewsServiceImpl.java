package com.imi.service.review.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.imi.base.model.reviews.ReviewsInfo;
import com.imi.dao.products.IProductDao;
import com.imi.dao.review.IReviewsDao;
import com.imi.dao.security.IRoleDao;
import com.imi.entity.products.Product;
import com.imi.entity.products.Reviews;
import com.imi.entity.security.Role;
import com.imi.model.product.ProductInfo;
import com.imi.service.review.IReviewsService;
@Service
public class ReviewsServiceImpl implements IReviewsService {
	private static final Logger logger = Logger.getLogger(ReviewsServiceImpl.class);
	
	@Resource
	private IProductDao productDao;
	@Resource
	private IReviewsDao reviewsDao;
	@Resource
	private IRoleDao roleDao;
	 
	public IReviewsDao getReviewsDao() {
		return reviewsDao;
	}

	public void setReviewsDao(IReviewsDao reviewsDao) {
		this.reviewsDao = reviewsDao;
	}

	@Override
	public void updateReviews(Reviews reviews) {
		if(logger.isDebugEnabled()) logger.debug("ReviewsServiceImpl复查更新提交...");
		this.reviewsDao.update(reviews);
	}

	@Override
	public List<Reviews> findReviews(ReviewsInfo info) {
		if(logger.isDebugEnabled()) logger.debug("ReviewsServiceImpl提取复查列表...");
		return this.reviewsDao.findReviews(info);
	}

	@Override
	public Reviews findOneReview(Long id) {
		// TODO Auto-generated method stub
		return this.reviewsDao.load(Reviews.class, id);
	}

	@Override
	public Long total(ReviewsInfo info) {
		// TODO Auto-generated method stub
		return this.reviewsDao.total(info);
	}

	@Override
	public boolean  firstReviewMain(Product product){ //admin1 admin2 admin3
		if(product == null || product.getProductType() == 2){
			return false;
		}
		//获取数量
		ProductInfo info = new ProductInfo();
		info.setProductType(1);
		long num = productDao.total(info);
		//创建reviews
		Reviews review = new Reviews();
		String hql = "from Role o where o.code = ?";
		Role role1 = roleDao.findUnique(hql,"admin1");
		Role role2 = roleDao.findUnique(hql,"admin2");
		//Role role3 = roleDao.findUnique(hql,"admin3");
		review.setProduct(product);
		review.setStatus(0);
		review.setModifiedTime(new Date());
		//根据规则分给admin1和admin2
		if(num%2 == 0){
			review.setRole(role1);
		}else{
			review.setRole(role2);
		}
		this.reviewsDao.save(review);
		return true;
	}
	
	@Override
	public boolean SecondReviewMain(Reviews reviews){
		//获取admin3 role
		String hql = "from Role o where o.code = ?";
		Role role3 = roleDao.findUnique(hql,"admin3");
		 
		//获取数量
		ReviewsInfo info = new ReviewsInfo();
		Product product=new Product();
		product.setProductType(1);
		info.setProduct(product);
		if("admin1".equals(reviews.getRole().getCode())){
			info.setFlowCheck1(1);
		}else if("admin2".equals(reviews.getRole().getCode())){
			info.setFlowCheck2(1);
		}else{
			return false;
		}
		long num = reviewsDao.total(info);
		//每3条选取1条给admin3复查
		if(num%3 == 1){
			reviews.setRole(role3);
			reviews.setStatus(0);
		}
		if(reviews.getId() != null){
			reviewsDao.update(reviews);
		}else{
			reviewsDao.save(reviews);
		}
		return true;
	}
	@Override
	public boolean firstReviewExt(Product product){
		if(product == null || product.getProductType() == 1){
			return false;
		}
		//获取数量
		ProductInfo info = new ProductInfo();
		info.setProductType(2);
		long num = productDao.total(info);
		//创建review
		Reviews review = new Reviews();
		String hql = "from Role o where o.code = ?";
		Role role1 = roleDao.findUnique(hql,"admin1");
		Role role2 = roleDao.findUnique(hql,"admin2");
		review.setProduct(product);
		review.setStatus(0);
		review.setModifiedTime(new Date());
		//根据规则分给admin1 admin2 admin3
		if(num%2 == 0){
			review.setRole(role1);
		}else{
			review.setRole(role2);
		}
		if(review.getRole() != null){
			this.reviewsDao.save(review);
		}
		return true;
	}
	
	@Override
	public boolean SecondReviewExt(Reviews reviews){
		//获取admin3 role
		String hql = "from Role o where o.code = ?";
		Role role3 = roleDao.findUnique(hql,"admin3");
		 
		//获取数量
		ReviewsInfo info = new ReviewsInfo();
		Product product=new Product();
		product.setProductType(2);
		info.setProduct(product);
		if("admin1".equals(reviews.getRole().getCode())){
			info.setFlowCheck1(1);
		}else if("admin2".equals(reviews.getRole().getCode())){
			info.setFlowCheck2(1);
		}else{
			return false;
		}
		long num = reviewsDao.total(info);
		//每3条选取1条给admin3复查
		if(num%3 == 1){
			reviews.setRole(role3);
			reviews.setStatus(0);
		}
		if(reviews.getId() != null){
			reviewsDao.update(reviews);
		}else{
			reviewsDao.save(reviews);
		}
		return true;
	}

	@Override
	public boolean firstReviewOth(Product product){
		if(product == null || product.getProductType() == 1){
			return false;
		}
		//获取数量
		ProductInfo info = new ProductInfo();
		info.setProductType(3);
		long num = productDao.total(info);
		//创建review
		Reviews review = new Reviews();
		String hql = "from Role o where o.code = ?";
		Role role1 = roleDao.findUnique(hql,"admin1");
		Role role2 = roleDao.findUnique(hql,"admin2");
		review.setProduct(product);
		review.setStatus(0);
		review.setModifiedTime(new Date());
		//根据规则分给admin1 admin2 admin3
		if(num%2 == 0){
			review.setRole(role1);
		}else{
			review.setRole(role2);
		}
		if(review.getRole() != null){
			this.reviewsDao.save(review);
		}
		return true;
	}

	@Override
	public boolean SecondReviewOth(Reviews reviews){
		//获取admin3 role
		String hql = "from Role o where o.code = ?";
		Role role3 = roleDao.findUnique(hql,"admin3");

		//获取数量
		ReviewsInfo info = new ReviewsInfo();
		Product product=new Product();
		product.setProductType(3);
		info.setProduct(product);
		if("admin1".equals(reviews.getRole().getCode())){
			info.setFlowCheck1(1);
		}else if("admin2".equals(reviews.getRole().getCode())){
			info.setFlowCheck2(1);
		}else{
			return false;
		}
		long num = reviewsDao.total(info);
		//每3条选取1条给admin3复查
		if(num%3 == 1){
			reviews.setRole(role3);
			reviews.setStatus(0);
		}
		if(reviews.getId() != null){
			reviewsDao.update(reviews);
		}else{
			reviewsDao.save(reviews);
		}
		return true;
	}
}
