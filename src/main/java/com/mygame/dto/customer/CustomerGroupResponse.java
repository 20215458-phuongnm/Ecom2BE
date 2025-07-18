package com.mygame.dto.customer;

import lombok.Data;

import java.time.Instant;

@Data
public class CustomerGroupResponse {
    private Long id;
    private Instant createdAt;
    private Instant updatedAt;
    private String code;
    private String name;
    private String description;
    private String color;
    private Integer status;
}
