package com.mygame.dto.authentication;

import lombok.Data;

@Data
public class RegistrationRequest {
    private Long userId;
    private String token;
}
