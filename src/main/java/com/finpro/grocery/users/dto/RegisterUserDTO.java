package com.finpro.grocery.users.dto;

import com.finpro.grocery.users.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Role is required")
    @Enumerated(EnumType.STRING)
    private User.UserRole role;

    private String referralCode;

    public User toEntity(){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);

        return user;
    }
}
