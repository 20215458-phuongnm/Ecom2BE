package com.mygame.repository.order;

import com.mygame.entity.order.OrderResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderResourceRepository extends JpaRepository<OrderResource, Long>, JpaSpecificationExecutor<OrderResource> {}
