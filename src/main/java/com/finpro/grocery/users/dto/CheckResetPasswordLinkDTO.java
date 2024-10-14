package com.finpro.grocery.users.dto;

import lombok.Data;

@Data
public class CheckResetPasswordLinkDTO {
    private String id;
    private String token;
}
