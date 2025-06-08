package com.mygame.dto.inventory;

import lombok.Data;

@Data
public class PurchaseOrderVariantKeyRequest {
    private Long purchaseOrderId;
    private Long variantId;
}
