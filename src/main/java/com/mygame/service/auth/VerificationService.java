package com.mygame.service.auth;

import com.mygame.dto.authentication.RegistrationRequest;
import com.mygame.dto.authentication.ResetPasswordRequest;
import com.mygame.dto.authentication.UserRequest;

public interface VerificationService {

    Long generateTokenVerify(UserRequest userRequest);

    void resendRegistrationToken(Long userId);

    void confirmRegistration(RegistrationRequest registration);

    void changeRegistrationEmail(Long userId, String emailUpdate);

    void forgetPassword(String email);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

}
