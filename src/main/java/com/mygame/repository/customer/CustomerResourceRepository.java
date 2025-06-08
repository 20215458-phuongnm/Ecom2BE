package com.mygame.repository.customer;

import com.mygame.entity.customer.CustomerResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerResourceRepository extends JpaRepository<CustomerResource, Long>, JpaSpecificationExecutor<CustomerResource> {
}