package com.mygame.controller.client;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mygame.constant.AppConstants;
import com.mygame.dto.authentication.UserResponse;
import com.mygame.dto.client.ClientEmailSettingUserRequest;
import com.mygame.dto.client.ClientPasswordSettingUserRequest;
import com.mygame.dto.client.ClientPersonalSettingUserRequest;
import com.mygame.dto.client.ClientPhoneSettingUserRequest;
import com.mygame.entity.authentication.User;
import com.mygame.mapper.authentication.UserMapper;
import com.mygame.repository.authentication.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client-api/users")
@AllArgsConstructor
@CrossOrigin(AppConstants.FRONTEND_HOST)
public class ClientUserController {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/personal")
    public ResponseEntity<UserResponse> updatePersonalSetting(Authentication authentication,
                                                              @RequestBody ClientPersonalSettingUserRequest userRequest) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(existingUser -> userMapper.partialUpdate(existingUser, userRequest))
                .map(userRepository::save)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/phone")
    public ResponseEntity<UserResponse> updatePhoneSetting(Authentication authentication,
                                                           @RequestBody ClientPhoneSettingUserRequest userRequest) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(existingUser -> userMapper.partialUpdate(existingUser, userRequest))
                .map(userRepository::save)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/email")
    public ResponseEntity<UserResponse> updateEmailSetting(Authentication authentication,
                                                           @RequestBody ClientEmailSettingUserRequest userRequest) {
        String username = authentication.getName();
        UserResponse userResponse = userRepository.findByUsername(username)
                .map(existingUser -> userMapper.partialUpdate(existingUser, userRequest))
                .map(userRepository::save)
                .map(userMapper::entityToResponse)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping("/password")
    public ResponseEntity<ObjectNode> updatePasswordSetting(Authentication authentication,
                                                            @RequestBody ClientPasswordSettingUserRequest userRequest) throws Exception {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (passwordEncoder.matches(userRequest.getOldPassword(), user.getPassword())) {
            String encodedNewPassword = passwordEncoder.encode(userRequest.getNewPassword());
            user.setPassword(encodedNewPassword);
            userRepository.save(user);

            // Source: https://stackoverflow.com/a/74827122
            // More: https://codingexplained.com/coding/java/spring-framework/returning-empty-json-object-spring-framework
            return ResponseEntity.status(HttpStatus.OK).body(new ObjectNode(JsonNodeFactory.instance));
        } else {
            throw new Exception("Wrong old password");
        }
    }

}
