package com.mygame.service.inventory;

import com.mygame.dto.order.OrderVariantRequest;
import com.mygame.dto.order.OrderVariantResponse;
import com.mygame.entity.order.OrderVariantKey;
import com.mygame.service.CrudService;

public interface OrderVariantService extends CrudService<OrderVariantKey, OrderVariantRequest, OrderVariantResponse> {}
