package com.finpro.grocery.auth.service.impl;

import com.finpro.grocery.auth.entity.VerificationToken;
import com.finpro.grocery.auth.repository.VerificationTokenRepository;
import com.finpro.grocery.auth.service.VerificationService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class VerificationServiceImpl implements VerificationService {
    private final VerificationTokenRepository verificationTokenRepository;
    public VerificationServiceImpl(VerificationTokenRepository verificationTokenRepository){
        this.verificationTokenRepository = verificationTokenRepository;
    }
    @Override
    public VerificationToken saveToken(VerificationToken token) {
        return verificationTokenRepository.save(token);
    }

    @Override
    public VerificationToken getTokenByValue(String value) {
        return verificationTokenRepository.findByValue(value).orElseThrow(() -> new UsernameNotFoundException("Token not found"));
    }
}
