package com.mygame.dto.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class DocketVariantResponse {
    private VariantResponse variant;
    private Integer quantity;

    @Data
    public static class VariantResponse {
        private Long id;
        private Instant createdAt;
        private Instant updatedAt;
        private ProductResponse product;
        private String sku;
        private Double cost;
        private Double price;
        @Nullable
        private JsonNode properties;
        private Integer status;

        @Data
        public static class ProductResponse {
            private Long id;
            private Instant createdAt;
            private Instant updatedAt;
            private String name;
            private String code;
            private String slug;
        }
    }
}
