package com.mygame.controller.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mygame.constant.AppConstants;
import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.dto.ListResponse;
import com.mygame.dto.client.ClientConfirmedOrderResponse;
import com.mygame.dto.client.ClientOrderDetailResponse;
import com.mygame.dto.client.ClientSimpleOrderRequest;
import com.mygame.dto.client.ClientSimpleOrderResponse;
import com.mygame.entity.general.Notification;
import com.mygame.entity.general.NotificationType;
import com.mygame.entity.order.Order;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.mapper.client.ClientOrderMapper;
import com.mygame.mapper.general.NotificationMapper;
import com.mygame.repository.general.NotificationRepository;
import com.mygame.repository.order.OrderRepository;
import com.mygame.service.general.NotificationService;
import com.mygame.service.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/client-api/orders")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientOrderController {

    private OrderRepository orderRepository;
    private ClientOrderMapper clientOrderMapper;
    private OrderService orderService;
    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private NotificationMapper notificationMapper;

    @GetMapping
    public ResponseEntity<ListResponse<ClientSimpleOrderResponse>> getAllOrders(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        Page<Order> orders = orderRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<ClientSimpleOrderResponse> clientReviewResponses = orders.map(clientOrderMapper::entityToResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientReviewResponses, orders));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ClientOrderDetailResponse> getOrder(@PathVariable String code) {
        ClientOrderDetailResponse clientOrderDetailResponse = orderRepository.findByCode(code)
                .map(clientOrderMapper::entityToDetailResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.ORDER, FieldName.ORDER_CODE, code));
        return ResponseEntity.status(HttpStatus.OK).body(clientOrderDetailResponse);
    }

    @PutMapping("/cancel/{code}")
    public ResponseEntity<ObjectNode> cancelOrder(@PathVariable String code) {
        orderService.cancelOrder(code);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @PostMapping
    public ResponseEntity<ClientConfirmedOrderResponse> createClientOrder(@RequestBody ClientSimpleOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createClientOrder(request));
    }

    @GetMapping(value = "/success")
    public RedirectView paymentSuccessAndCaptureTransaction(HttpServletRequest request) {
        String paypalOrderId = request.getParameter("token");
        String payerId = request.getParameter("PayerID");

        orderService.captureTransactionPaypal(paypalOrderId, payerId);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(AppConstants.FRONTEND_HOST + "/payment/success");
        return redirectView;
    }

    @GetMapping(value = "/cancel")
    public RedirectView paymentCancel(HttpServletRequest request) {
        String paypalOrderId = request.getParameter("token");

        Order order = orderRepository.findByPaypalOrderId(paypalOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.ORDER, FieldName.PAYPAL_ORDER_ID, paypalOrderId));

        Notification notification = new Notification()
                .setUser(order.getUser())
                .setType(NotificationType.CHECKOUT_PAYPAL_CANCEL)
                .setMessage(String.format("Bạn đã hủy thanh toán PayPal cho đơn hàng %s.", order.getCode()))
                .setAnchor("/order/detail/" + order.getCode())
                .setStatus(1);

        notificationRepository.save(notification);

        notificationService.pushNotification(order.getUser().getUsername(),
                notificationMapper.entityToResponse(notification));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(AppConstants.FRONTEND_HOST + "/payment/cancel");
        return redirectView;
    }

}
