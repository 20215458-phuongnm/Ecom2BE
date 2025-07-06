package com.mygame.controller.authentication;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mygame.config.security.JwtUtils;
import com.mygame.constant.AppConstants;
import com.mygame.dto.authentication.*;
import com.mygame.entity.authentication.RefreshToken;
import com.mygame.entity.authentication.User;
import com.mygame.exception.RefreshTokenException;
import com.mygame.mapper.authentication.UserMapper;
import com.mygame.repository.authentication.UserRepository;
import com.mygame.service.auth.VerificationService;
import com.mygame.service.authetication.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class AuthController {

    private AuthenticationManager authenticationManager;
    private VerificationService verificationService;
    private RefreshTokenService refreshTokenService;
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private UserMapper userMapper;

    /**
     * Đăng nhập hệ thống:
     * - Nhận username và password từ client.
     * - Xác thực người dùng thông qua AuthenticationManager.
     * - Nếu thành công, sinh ra accessToken (JWT) và refreshToken.
     * - Trả về JwtResponse chứa token và thời gian.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication).getToken();

        return ResponseEntity.ok(new JwtResponse("Login success!", jwt, refreshToken, Instant.now()));
    }

    /**
     * Làm mới access token từ refresh token:
     * - Nhận refreshToken từ client.
     * - Kiểm tra tính hợp lệ và hạn sử dụng.
     * - Nếu hợp lệ, tạo mới accessToken và trả về cho client.
     */
    @PostMapping("/refresh-token")//ModelAttribute
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        String jwt = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(User::getUsername)
                .map(jwtUtils::generateTokenFromUsername)
                .orElseThrow(() -> new RefreshTokenException("Refresh token was expired. Please make a new signin request!"));

        return ResponseEntity.ok(new JwtResponse("Refresh token", jwt, refreshToken, Instant.now()));
    }

    /**
     * Đăng ký tài khoản:
     * - Nhận thông tin người dùng
     * - Tạo tài khoản mới ở trạng thái chờ xác minh.
     * - Sinh và gửi token xác minh qua email.
     * - Trả về userId để client theo dõi đăng ký.
     */
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody UserRequest userRequest) {
        Long userId = verificationService.generateTokenVerify(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponse(userId));
    }

    /**
     * Gửi lại token xác nhận đăng ký:
     * - Khi người dùng chưa xác nhận email, gọi API này để gửi lại token.
     * - Dựa vào userId để tìm lại thông tin người dùng.
     */
    @GetMapping("/registration/{userId}/resend-token")
    public ResponseEntity<ObjectNode> resendRegistrationToken(@PathVariable Long userId) {
        verificationService.resendRegistrationToken(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    /**
     * Xác nhận đăng ký tài khoản:
     * - Nhận username và token từ email xác minh.
     * - Kiểm tra và xác nhận tài khoản.
     */
    @PostMapping("/registration/confirm")
    public ResponseEntity<ObjectNode> confirmRegistration(@RequestBody RegistrationRequest registration) {
        verificationService.confirmRegistration(registration);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    /**
     * Đổi email đăng ký:
     * - Khi người dùng nhập sai email lúc đăng ký.
     * - Gửi lại token xác minh tới email mới.
     */
    @PutMapping("/registration/{userId}/change-email")
    public ResponseEntity<ObjectNode> changeRegistrationEmail(@PathVariable Long userId, @RequestParam String email) {
        verificationService.changeRegistrationEmail(userId, email);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    /**
     * Gửi email quên mật khẩu:
     * - Nhận email từ client.
     * - Nếu email tồn tại, gửi email chứa link reset mật khẩu.
     */
    @GetMapping("/forgot-password")
    public ResponseEntity<ObjectNode> forgotPassword(@RequestParam String email) {
        verificationService.forgetPassword(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    /**
     * Đặt lại mật khẩu:
     * - Nhận token reset và mật khẩu mới từ client.
     * - Kiểm tra token và đổi mật khẩu cho tài khoản tương ứng.
     */
    @PutMapping("/reset-password")
    public ResponseEntity<ObjectNode> resetPassword(@RequestBody ResetPasswordRequest resetPassword) {
        verificationService.resetPassword(resetPassword);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    /**
     * Lấy thông tin người dùng hiện tại:
     * - Dựa vào JWT trong Authentication object.
     * - Trả về thông tin user tương ứng (username, email,...).
     */
    @GetMapping("/info")
    public ResponseEntity<UserResponse> getAdminUserInfo(Authentication authentication) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
