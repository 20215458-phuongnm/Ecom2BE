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

    // API: Lấy danh sách đơn hàng của user hiện tại
    @GetMapping
    public ResponseEntity<ListResponse<ClientSimpleOrderResponse>> getAllOrders(
            Authentication authentication,
            @RequestParam(name = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "sort", defaultValue = AppConstants.DEFAULT_SORT) String sort,
            @RequestParam(name = "filter", required = false) @Nullable String filter
    ) {
        String username = authentication.getName();
        // Truy vấn danh sách đơn hàng theo username
        Page<Order> orders = orderRepository.findAllByUsername(username, sort, filter, PageRequest.of(page - 1, size));
        List<ClientSimpleOrderResponse> clientReviewResponses = orders.map(clientOrderMapper::entityToResponse).toList();  // Chuyển sang DTO ClientSimpleOrderResponse
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.of(clientReviewResponses, orders));
    }

    // API: Lấy thông tin chi tiết của một đơn hàng dựa vào mã đơn
    @GetMapping("/{code}")
    public ResponseEntity<ClientOrderDetailResponse> getOrder(@PathVariable String code) {
        ClientOrderDetailResponse clientOrderDetailResponse = orderRepository.findByCode(code)
                .map(clientOrderMapper::entityToDetailResponse)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.ORDER, FieldName.ORDER_CODE, code));
        return ResponseEntity.status(HttpStatus.OK).body(clientOrderDetailResponse);
    }

    // API: Hủy một đơn hàng dựa vào mã đơn hàng
    @PutMapping("/cancel/{code}")
    public ResponseEntity<ObjectNode> cancelOrder(@PathVariable String code) {
        orderService.cancelOrder(code);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance)); // Trả về object rỗng (200 OK)
    }

    // API: Tạo đơn hàng từ phía client
    @PostMapping
    public ResponseEntity<ClientConfirmedOrderResponse> createClientOrder(@RequestBody ClientSimpleOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createClientOrder(request));
    }

    // API: Sau khi thanh toán PayPal thành công → capture transaction
    // => gọi từ trang xác nhận thành công của PayPal
    @GetMapping(value = "/success")
    public RedirectView paymentSuccessAndCaptureTransaction(HttpServletRequest request) {
        String paypalOrderId = request.getParameter("token"); // Lấy mã đơn từ query PayPal
        String payerId = request.getParameter("PayerID"); // Lấy mã người thanh toán từ query

        // Thực hiện capture thanh toán (ghi nhận tiền)
        orderService.captureTransactionPaypal(paypalOrderId, payerId);

        // Redirect về trang thành công bên fe
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(AppConstants.FRONTEND_HOST + "/payment/success");
        return redirectView;
    }

    // API: Khi thanh toán PayPal bị hủy
    @GetMapping(value = "/cancel")
    public RedirectView paymentCancel(HttpServletRequest request) {
        String paypalOrderId = request.getParameter("token");
        // Tìm đơn hàng theo PayPal order ID
        Order order = orderRepository.findByPaypalOrderId(paypalOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.ORDER, FieldName.PAYPAL_ORDER_ID, paypalOrderId));
        // Tạo một notification thông báo người dùng đã hủy đơn
        Notification notification = new Notification()
                .setUser(order.getUser())
                .setType(NotificationType.CHECKOUT_PAYPAL_CANCEL)
                .setMessage(String.format("Bạn đã hủy thanh toán PayPal cho đơn hàng %s.", order.getCode()))
                .setAnchor("/order/detail/" + order.getCode())
                .setStatus(1); // trạng thái chưa đọc

        notificationRepository.save(notification);

        notificationService.pushNotification(order.getUser().getUsername(),
                notificationMapper.entityToResponse(notification));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(AppConstants.FRONTEND_HOST + "/payment/cancel");
        return redirectView;
    }

}
