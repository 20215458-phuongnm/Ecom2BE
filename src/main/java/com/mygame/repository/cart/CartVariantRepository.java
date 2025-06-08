package com.mygame.repository.cart;

import com.mygame.entity.cart.CartVariant;
import com.mygame.entity.cart.CartVariantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartVariantRepository extends JpaRepository<CartVariant, CartVariantKey>,
        JpaSpecificationExecutor<CartVariant> {}
