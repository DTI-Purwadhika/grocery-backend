package com.finpro.grocery.auth.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String VERIFICATION_LINK_PREFIX = "VERIFICATION_LINK:";
    private static final String RESET_PASSWORD_LINK_PREFIX = "RESET_PASSWORD_LINK:";
    private static final String JWT_PREFIX = "JWT:";
    private static final String BLACKLIST_JWT_PREFIX = "BLACKLIST_JWT:";

    public AuthRedisService(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public String saveVerificationLink(String email){
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(VERIFICATION_LINK_PREFIX + email, token, 1, TimeUnit.HOURS);
        return token;
    }

    public String getVerificationLink(String email){
        return redisTemplate.opsForValue().get(VERIFICATION_LINK_PREFIX + email);
    }

    public Boolean isVerificationLinkValid(String email){
        return redisTemplate.hasKey(VERIFICATION_LINK_PREFIX + email);
    }

    public void deleteVerificationLink(String email){
        redisTemplate.opsForValue().getOperations().delete(VERIFICATION_LINK_PREFIX + email);
    }

    public String saveResetPasswordLink(String email){
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(RESET_PASSWORD_LINK_PREFIX + email, token, 1, TimeUnit.HOURS);
        return token;
    }

    public String getResetPasswordLink(String email){
        return redisTemplate.opsForValue().get(RESET_PASSWORD_LINK_PREFIX + email);
    }

    public Boolean isResetPasswordLinkValid(String email){
        return redisTemplate.hasKey(RESET_PASSWORD_LINK_PREFIX + email);
    }

    public void deleteResetPasswordLink(String email){
        redisTemplate.opsForValue().getOperations().delete(RESET_PASSWORD_LINK_PREFIX + email);
    }

    public void saveJwt(String email, String jwt){
        redisTemplate.opsForValue().set(JWT_PREFIX + email, jwt, 5, TimeUnit.HOURS);
    }

    public String getJwt(String email){
        return redisTemplate.opsForValue().get(JWT_PREFIX + email);
    }

    public void blacklistJwt(String email, String jwt){
        redisTemplate.opsForValue().set(BLACKLIST_JWT_PREFIX + jwt, email, 1, TimeUnit.HOURS);
    }

    public Boolean isJwtBlacklisted(String jwt){
        return redisTemplate.hasKey(BLACKLIST_JWT_PREFIX + jwt);
    }

}
