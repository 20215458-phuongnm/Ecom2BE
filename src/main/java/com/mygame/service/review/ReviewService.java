package com.mygame.service.review;

import com.mygame.dto.review.ReviewRequest;
import com.mygame.dto.review.ReviewResponse;
import com.mygame.service.CrudService;

public interface ReviewService extends CrudService<Long, ReviewRequest, ReviewResponse> {}
