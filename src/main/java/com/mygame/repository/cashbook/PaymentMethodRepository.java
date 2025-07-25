package com.mygame.repository.cashbook;

import com.mygame.entity.cashbook.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>, JpaSpecificationExecutor<PaymentMethod> {

    List<PaymentMethod> findAllByStatus(Integer status);

}
