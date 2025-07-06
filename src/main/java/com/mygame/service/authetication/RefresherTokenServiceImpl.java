package com.mygame.service.authetication;

import com.mygame.constant.FieldName;
import com.mygame.constant.ResourceName;
import com.mygame.entity.authentication.RefreshToken;
import com.mygame.exception.RefreshTokenException;
import com.mygame.exception.ResourceNotFoundException;
import com.mygame.repository.authentication.RefreshTokenRepository;
import com.mygame.repository.authentication.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefresherTokenServiceImpl implements RefreshTokenService {

    // Thời gian hết hạn của refresh token được lấy từ file cấu hình application.properties
    @Value("${electro.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Tìm kiếm một RefreshToken dựa trên chuỗi token được truyền vào.
     * Trả về Optional chứa RefreshToken nếu tồn tại, ngược lại là Optional rỗng.
     */
    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Tạo một RefreshToken mới cho user đang đăng nhập.
     * - Lấy username từ thông tin xác thực.
     * - Tìm user trong DB.
     * - Tạo token ngẫu nhiên, set thời gian hết hạn.
     * - Lưu vào cơ sở dữ liệu.
     */
    @Override
    public RefreshToken createRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findByUsername(username) // Gán user
                .orElseThrow(() -> new ResourceNotFoundException(ResourceName.USER, FieldName.USERNAME, username)));
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString()); // Tạo chuỗi token ngẫu nhiên UUID

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Kiểm tra thời hạn của một RefreshToken:
     * - Nếu đã hết hạn → xóa khỏi DB và ném lỗi.
     * - Nếu còn hạn → trả về token.
     */
    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Refresh token was expired. Please make a new signin request!");
        }

        return refreshToken;
    }
}
