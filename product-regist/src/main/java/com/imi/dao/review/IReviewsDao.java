package com.imi.dao.review;

import java.util.List;

import com.imi.base.IBaseDao;
import com.imi.base.model.reviews.ReviewsInfo;
import com.imi.entity.products.Reviews;

/**
 * 复查数据接口。
 * @author josh.
 * @since 2014-05-08.
 */
public interface IReviewsDao extends IBaseDao<Reviews> {
	
	List<Reviews> findReviews(ReviewsInfo info);
	Long total(ReviewsInfo info);
	
}