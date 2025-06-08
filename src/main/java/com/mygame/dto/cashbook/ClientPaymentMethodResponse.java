package com.mygame.dto.cashbook;

import com.mygame.entity.cashbook.PaymentMethodType;
import lombok.Data;

@Data
public class ClientPaymentMethodResponse {
    private Long paymentMethodId;
    private String paymentMethodName;
    private PaymentMethodType paymentMethodCode;
}
