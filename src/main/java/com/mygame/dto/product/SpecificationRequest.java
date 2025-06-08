package com.mygame.dto.product;

import lombok.Data;

@Data
public class SpecificationRequest {
    private String name;
    private String code;
    private String description;
    private Integer status;
}
