package com.mygame.dto.address;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class AddressResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    @Nullable
    private String line;
    @Nullable
    private ProvinceResponse province;
    @Nullable
    private DistrictResponse district;
    @Nullable
    private WardResponse ward;

    @Data
    public static class DistrictResponse {
        private Long id;
        private Instant createdAt;
        private Instant updatedAt;
        private String name;
        private String code;
    }

    @Data
    public static class WardResponse {
        private Long id;
        private Instant createdAt;
        private Instant updatedAt;
        private String name;
        private String code;
    }
}
