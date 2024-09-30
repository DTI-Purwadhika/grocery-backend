package com.finpro.grocery.auth.dto;

import lombok.Data;

@Data
public class SocialLoginResponseDTO {
    private String email;
    private String token;
    private String role;
    private String referralCode;
    private Boolean isVerified;
}
