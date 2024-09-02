package com.finpro.grocery.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SetPasswordDTO {
    @NotBlank(message = "Password is required")
    private String password;
}
