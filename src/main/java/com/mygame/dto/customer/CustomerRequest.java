package com.mygame.dto.customer;

import com.mygame.dto.authentication.UserRequest;
import lombok.Data;

@Data
public class CustomerRequest {
    private UserRequest user;
    private Long customerGroupId;
    private Long customerStatusId;
    private Long customerResourceId;
}
