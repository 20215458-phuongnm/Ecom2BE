package com.mygame.repository.product;

import com.mygame.entity.product.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpecificationRepository extends JpaRepository<Specification, Long>, JpaSpecificationExecutor<Specification> {
}
