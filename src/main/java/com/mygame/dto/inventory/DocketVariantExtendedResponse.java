package com.mygame.dto.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class DocketVariantExtendedResponse {
    private DocketResponse docket;
    private VariantResponse variant;
    private Integer quantity;

    @Data
    public static class DocketResponse {
        private Long id;
        private Instant createdAt;
        private Instant updatedAt;
        private Integer type;
        private String code;
        private DocketReasonResponse reason;
        private WarehouseResponse warehouse;
        @Nullable
        private PurchaseOrderResponse purchaseOrder;
        @Nullable
        private OrderResponse order;
        private Integer status;

        @Data
        public static class PurchaseOrderResponse {
            private Long id;
            private Instant createdAt;
            private Instant updatedAt;
            private String code;
            private Integer status;
        }

        @Data
        public static class OrderResponse {
            private Long id;
            private Instant createdAt;
            private Instant updatedAt;
            private String code;
            private Integer status;
        }
    }

    @Data
    public static class VariantResponse {
        private Long id;
        private Instant createdAt;
        private Instant updatedAt;
        private String sku;
        @Nullable
        private JsonNode properties;
        private Integer status;
    }
}
