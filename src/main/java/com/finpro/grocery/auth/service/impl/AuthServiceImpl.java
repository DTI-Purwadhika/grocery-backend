package com.finpro.grocery.auth.service.impl;

import com.finpro.grocery.auth.dto.SocialLoginRequestDTO;
import com.finpro.grocery.auth.dto.SocialLoginResponseDTO;
import com.finpro.grocery.auth.helper.Claims;
import com.finpro.grocery.auth.service.AuthService;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.service.UserService;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class AuthServiceImpl implements AuthService {
    private final JwtEncoder jwtEncoder;
    private final AuthRedisService authRedisService;
    private final UserService userService;

    public AuthServiceImpl(JwtEncoder jwtEncoder, AuthRedisService authRedisService, UserService userService){
        this.jwtEncoder = jwtEncoder;
        this.authRedisService = authRedisService;
        this.userService = userService;
    }
    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

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
    public String generateSocialToken(String email) {
        Instant now = Instant.now();

        User user = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email not found"));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("grocery")
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.HOURS))
                .subject(user.getEmail())
                .claim("scope", user.getRole().name())
                .build();

        String tokenResult = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        authRedisService.saveJwt(user.getEmail(), tokenResult);

        return tokenResult;
    }

    @Transactional
    @Override
    public SocialLoginResponseDTO socialLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
        Optional<User> user = userService.getUserByEmail(socialLoginRequestDTO.getEmail());

        SocialLoginResponseDTO socialLoginResponseDTO = new SocialLoginResponseDTO();

        if(user.isPresent()){
            user.get().setName(socialLoginRequestDTO.getName());
            userService.saveUser(user.get());

            String token = generateSocialToken(socialLoginRequestDTO.getEmail());
            socialLoginResponseDTO.setEmail(socialLoginRequestDTO.getEmail());
            socialLoginResponseDTO.setRole(socialLoginRequestDTO.getRole().name());
            socialLoginResponseDTO.setToken(token);
            socialLoginResponseDTO.setIsVerified(user.get().getIsVerified());
            socialLoginResponseDTO.setReferralCode(user.get().getReferralCode());
        }
        else{
            userService.saveUserSocialLogin(socialLoginRequestDTO);
            String token = generateSocialToken(socialLoginRequestDTO.getEmail());
            socialLoginResponseDTO.setEmail(socialLoginRequestDTO.getEmail());
            socialLoginResponseDTO.setRole(socialLoginRequestDTO.getRole().name());
            socialLoginResponseDTO.setToken(token);
        }

        return socialLoginResponseDTO;
    }

    @Override
    public void logout() {
        var claims = Claims.getClaimsFromJwt();
        String email = (String) claims.get("sub");

        String jwt = authRedisService.getJwt(email);
        authRedisService.blacklistJwt(email, jwt);
    }
}
