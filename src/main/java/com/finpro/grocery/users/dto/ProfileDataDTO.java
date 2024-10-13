package com.finpro.grocery.users.dto;

import com.finpro.grocery.users.entity.User;
import lombok.Data;

@Data
public class ProfileDataDTO {
    private Long id;
    private String name;
    private String email;
    private User.UserRole role;
    private String profilePicture;
    private String referralCode;
    private Boolean isVerified;
    private String error = "None";

    public static ProfileDataDTO toDto(User user){
        ProfileDataDTO profileDataDTO = new ProfileDataDTO();
        profileDataDTO.setId(user.getId());
        profileDataDTO.setName(user.getName());
        profileDataDTO.setEmail(user.getEmail());
        profileDataDTO.setRole(user.getRole());
        profileDataDTO.setReferralCode(user.getReferralCode());
        profileDataDTO.setIsVerified(user.getIsVerified());
        return profileDataDTO;
    }
}
