package com.finpro.grocery.auth.service;

import com.finpro.grocery.auth.entity.VerificationToken;

public interface VerificationService {
    public VerificationToken saveToken(VerificationToken token);
    public VerificationToken getTokenByValue(String value);
}
