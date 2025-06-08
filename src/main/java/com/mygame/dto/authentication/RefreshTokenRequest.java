package com.mygame.dto.authentication;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
