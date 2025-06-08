package com.mygame.service.inventory;

import com.mygame.dto.inventory.PurchaseOrderVariantRequest;
import com.mygame.dto.inventory.PurchaseOrderVariantResponse;
import com.mygame.entity.inventory.PurchaseOrderVariantKey;
import com.mygame.service.CrudService;

public interface PurchaseOrderVariantService extends CrudService<PurchaseOrderVariantKey, PurchaseOrderVariantRequest,
        PurchaseOrderVariantResponse> {}
