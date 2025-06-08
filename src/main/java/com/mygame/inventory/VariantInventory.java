package com.mygame.inventory;

import com.mygame.entity.inventory.DocketVariant;
import com.mygame.entity.product.Variant;
import lombok.Data;

import java.util.List;

@Data
public class VariantInventory {
    private Variant variant;
    private List<DocketVariant> transactions;
    private Integer inventory;
    private Integer waitingForDelivery;
    private Integer canBeSold;
    private Integer areComing;
}
