package com.imi.service.review;

import java.util.List;

import com.imi.base.model.reviews.ReviewsInfo;
import com.imi.entity.products.Product;
import com.imi.entity.products.Reviews;
import com.imi.entity.security.Role;

public interface IReviewsService{
	
	void updateReviews(Reviews reviews);
	List<Reviews> findReviews(ReviewsInfo info);
	Long total(ReviewsInfo info);
	Reviews findOneReview(Long id);
	boolean firstReviewMain(Product product);
	boolean SecondReviewMain(Reviews reviews);
	boolean firstReviewExt(Product product);
	boolean SecondReviewExt(Reviews reviews);
	boolean firstReviewOth(Product product);
	boolean SecondReviewOth(Reviews reviews);
}
