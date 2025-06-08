package com.mygame.dto.employee;

import com.mygame.dto.address.AddressRequest;
import lombok.Data;

@Data
public class OfficeRequest {
    private String name;
    private AddressRequest address;
    private Integer status;
}
