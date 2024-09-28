package com.finpro.grocery.auth.dto;

import com.finpro.grocery.users.entity.User;
import lombok.Data;

@Data
public class SocialLoginRequestDTO {
    private String name;
    private String email;
    private User.UserRole role;
}
