package com.mygame.dto.product;

import lombok.Data;

import java.time.Instant;

@Data
public class SpecificationResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String name;
    private String code;
    private String description;
    private Integer status;
}
