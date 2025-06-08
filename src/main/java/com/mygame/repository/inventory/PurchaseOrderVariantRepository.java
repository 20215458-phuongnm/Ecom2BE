package com.mygame.repository.inventory;

import com.mygame.entity.inventory.PurchaseOrderVariant;
import com.mygame.entity.inventory.PurchaseOrderVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PurchaseOrderVariantRepository extends JpaRepository<PurchaseOrderVariant, PurchaseOrderVariantKey>,
        JpaSpecificationExecutor<PurchaseOrderVariant> {}
