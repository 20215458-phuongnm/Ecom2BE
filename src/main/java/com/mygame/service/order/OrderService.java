package com.mygame.service.order;

import com.mygame.dto.client.ClientConfirmedOrderResponse;
import com.mygame.dto.client.ClientSimpleOrderRequest;

public interface OrderService {

    void cancelOrder(String code);

    ClientConfirmedOrderResponse createClientOrder(ClientSimpleOrderRequest request);

    void captureTransactionPaypal(String paypalOrderId, String payerId);

}
