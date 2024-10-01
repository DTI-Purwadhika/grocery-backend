package com.finpro.grocery.users.dto;

import com.finpro.grocery.users.entity.User;
import lombok.Data;

@Data
public class ProfileDataDTO {
    private String name;
    private String email;
    private User.UserRole role;
    private String profilePicture;
    private String referralCode;
    private Boolean isVerified;
    private String error = "None";
}
