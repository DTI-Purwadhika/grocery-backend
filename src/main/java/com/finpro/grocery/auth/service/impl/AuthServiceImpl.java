package com.finpro.grocery.auth.service.impl;

import com.finpro.grocery.auth.helper.Claims;
import com.finpro.grocery.auth.service.AuthService;
import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@Log
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtEncoder jwtEncoder;
    private final AuthRedisService authRedisService;

    public AuthServiceImpl(UserRepository userRepository, JwtEncoder jwtEncoder, AuthRedisService authRedisService){
        this.userRepository = userRepository;
        this.jwtEncoder = jwtEncoder;
        this.authRedisService = authRedisService;
    }
    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

//        String existingToken = authRedisService.getJwt(authentication.getName());

//        if( existingToken != null ){
//            log.info("Token already exists for user: " + authentication.getName());
//            return existingToken;
//        }

        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("grocery")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        String tokenResult = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        authRedisService.saveJwt(authentication.getName(), tokenResult);

        return tokenResult;
    }

    @Override
    public void logout() {
        var claims = Claims.getClaimsFromJwt();
        String email = (String) claims.get("sub");

        String jwt = authRedisService.getJwt(email);
        authRedisService.blacklistJwt(email, jwt);
    }
}
