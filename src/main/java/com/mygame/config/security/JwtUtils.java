package com.mygame.config.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${electro.app.jwtSecret}")
    private String jwtSecret;

    @Value("${electro.app.jwtExpirationMs}")
    private int jwtExpiration;

    /**
     * generateJwtToken:
     * Tạo JWT token từ thông tin xác thực (Authentication).
     * - Lấy username từ `UserDetailsImpl`
     * - Gán issued time (ngày phát hành), expired time (ngày hết hạn)
     * - Ký bằng thuật toán HS512 + secret key
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();
    }

    /**
     * generateTokenFromUsername:  taok accesstoken từ refreshtoken
     * Tạo JWT từ username (dùng trong quá trình refresh token).
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                .compact();
    }

    //Lấy username từ token
    public String getUsernameFromJwt(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();// Dùng secret key để giải mã token
    }

    // Ktra jwt token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken); //giải mã ktra chữ ký
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Invalid JWT expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Invalid JWT unsupported {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Invalid JWT empty {}", e.getMessage());
        }

        return false;
    }

}
