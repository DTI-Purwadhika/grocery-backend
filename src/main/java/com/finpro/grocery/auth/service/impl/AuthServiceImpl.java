package com.finpro.grocery.auth.service.impl;

import com.finpro.grocery.auth.service.AuthService;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
import org.springframework.security.core.Authentication;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public String generateToken(Authentication authentication) {
        return null;
    }
}
