package com.mygame.controller.order;

import com.mygame.constant.AppConstants;
import com.mygame.dto.order.OrderVariantKeyRequest;
import com.mygame.entity.order.OrderVariantKey;
import com.mygame.service.inventory.OrderVariantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-variants")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class OrderVariantController {

    private OrderVariantService orderVariantService;

    // API: Xóa sp khỏi order
    @DeleteMapping("/{orderId}/{variantId}")
    public ResponseEntity<Void> deleteOrderVariant(@PathVariable("orderId") Long orderId,
                                                   @PathVariable("variantId") Long variantId) {
        OrderVariantKey id = new OrderVariantKey(orderId, variantId);
        orderVariantService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // API: Xóa sp khỏi order
    @DeleteMapping
    public ResponseEntity<Void> deleteOrderVariants(@RequestBody List<OrderVariantKeyRequest> idRequests) {
        List<OrderVariantKey> ids = idRequests.stream()
                .map(idRequest -> new OrderVariantKey(idRequest.getOrderId(), idRequest.getVariantId()))
                .collect(Collectors.toList());
        orderVariantService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
