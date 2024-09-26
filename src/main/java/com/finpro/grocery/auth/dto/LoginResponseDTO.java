package com.finpro.grocery.auth.dto;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String userId;
    private String email;
    private String token;
    private String role;
}
