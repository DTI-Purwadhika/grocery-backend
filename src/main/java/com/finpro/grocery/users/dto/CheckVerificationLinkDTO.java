package com.finpro.grocery.users.dto;

import lombok.Data;

@Data
public class CheckVerificationLinkDTO {
    private String email;
    private String token;
}
