package com.mygame.service.inventory;

import com.mygame.dto.inventory.DocketVariantRequest;
import com.mygame.dto.inventory.DocketVariantResponse;
import com.mygame.entity.inventory.DocketVariantKey;
import com.mygame.service.CrudService;

public interface DocketVariantService extends CrudService<DocketVariantKey, DocketVariantRequest, DocketVariantResponse> {}
