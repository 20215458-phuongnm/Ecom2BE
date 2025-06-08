package com.mygame.service.promotion;

import com.mygame.dto.promotion.PromotionRequest;
import com.mygame.dto.promotion.PromotionResponse;
import com.mygame.service.CrudService;

import java.time.Instant;

public interface PromotionService extends CrudService<Long, PromotionRequest, PromotionResponse> {

    boolean checkCanCreatePromotionForProduct(Long productId, Instant startDate, Instant endDate);

}
