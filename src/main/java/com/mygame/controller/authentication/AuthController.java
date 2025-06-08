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

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(authentication).getToken();

        return ResponseEntity.ok(new JwtResponse("Login success!", jwt, refreshToken, Instant.now()));
    }

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

    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody UserRequest userRequest) {
        Long userId = verificationService.generateTokenVerify(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new RegistrationResponse(userId));
    }

    @GetMapping("/registration/{userId}/resend-token")
    public ResponseEntity<ObjectNode> resendRegistrationToken(@PathVariable Long userId) {
        verificationService.resendRegistrationToken(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @PostMapping("/registration/confirm")
    public ResponseEntity<ObjectNode> confirmRegistration(@RequestBody RegistrationRequest registration) {
        verificationService.confirmRegistration(registration);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @PutMapping("/registration/{userId}/change-email")
    public ResponseEntity<ObjectNode> changeRegistrationEmail(@PathVariable Long userId, @RequestParam String email) {
        verificationService.changeRegistrationEmail(userId, email);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<ObjectNode> forgotPassword(@RequestParam String email) {
        verificationService.forgetPassword(email);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ObjectNode> resetPassword(@RequestBody ResetPasswordRequest resetPassword) {
        verificationService.resetPassword(resetPassword);
        return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getAdminUserInfo(Authentication authentication) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

}
