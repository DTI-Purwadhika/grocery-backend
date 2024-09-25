package com.finpro.grocery.auth.service;

import com.finpro.grocery.auth.dto.SocialLoginRequestDTO;
import com.finpro.grocery.auth.dto.SocialLoginResponseDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    String generateToken(Authentication authentication);
    String generateSocialToken(String email);
    SocialLoginResponseDTO socialLogin(SocialLoginRequestDTO socialLoginRequestDTO);
    void logout();
}
