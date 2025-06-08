package com.mygame.repository.order;

import com.mygame.entity.order.OrderVariant;
import com.mygame.entity.order.OrderVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderVariantRepository extends JpaRepository<OrderVariant, OrderVariantKey>,
        JpaSpecificationExecutor<OrderVariant> {}
