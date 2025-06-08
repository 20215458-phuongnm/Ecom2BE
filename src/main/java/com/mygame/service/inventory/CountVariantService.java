package com.mygame.service.inventory;

import com.mygame.dto.inventory.CountVariantRequest;
import com.mygame.dto.inventory.CountVariantResponse;
import com.mygame.entity.inventory.CountVariantKey;
import com.mygame.service.CrudService;

public interface CountVariantService extends CrudService<CountVariantKey, CountVariantRequest, CountVariantResponse> {}
