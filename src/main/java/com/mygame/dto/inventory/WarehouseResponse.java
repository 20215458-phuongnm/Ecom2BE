package com.mygame.dto.inventory;

import com.mygame.dto.address.AddressResponse;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Data
public class WarehouseResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String code;
    private String name;
    @Nullable
    private AddressResponse address;
    private Integer status;
}
