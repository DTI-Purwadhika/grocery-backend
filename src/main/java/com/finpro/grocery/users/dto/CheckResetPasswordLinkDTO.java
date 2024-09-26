package com.finpro.grocery.users.dto;

import lombok.Data;

@Data
public class CheckResetPasswordLinkDTO {
    private String email;
    private String token;
}
