package com.mygame.dto.employee;

import com.mygame.dto.address.AddressResponse;
import lombok.Data;

import java.time.Instant;

@Data
public class OfficeResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private AddressResponse address;
    private Integer status;
}
