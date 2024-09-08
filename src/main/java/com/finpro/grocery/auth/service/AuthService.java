package com.finpro.grocery.auth.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
    String generateToken(Authentication authentication);
    void logout();
}
