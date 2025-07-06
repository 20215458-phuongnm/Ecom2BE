package com.mygame.config.payment.paypal;

public enum PayPalEndpoints {
    GET_ACCESS_TOKEN("/v1/oauth2/token"),
    //Lấy token cho client-side PayPal SDK (khởi tạo PayPal button)
    GET_CLIENT_TOKEN("/v1/identity/generate-token"),
    //Tạo một đơn hàng (order) trong hệ thống PayPal
    ORDER_CHECKOUT("/v2/checkout/orders");

    private final String path;

    PayPalEndpoints(String path) {
        this.path = path;
    }

    // tạo URL đầy đủ khi biết baseUrl và endpoint
    public static String createUrl(String baseUrl, PayPalEndpoints endpoint) {
        return baseUrl + endpoint.path;
    }

    // Hàm tạo URL đầy đủ nếu endpoint có thêm tham số
    public static String createUrl(String baseUrl, PayPalEndpoints endpoint, String... params) {
        return baseUrl + String.format(endpoint.path, (Object[]) params);
    }
    // Hàm tạo URL để capture (chốt thanh toán) một order đã tạo
    public static String createCaptureUrl(String baseUrl, PayPalEndpoints endpoint, String token) {
        return baseUrl + endpoint.path + "/" + token + "/capture";
    }
}
