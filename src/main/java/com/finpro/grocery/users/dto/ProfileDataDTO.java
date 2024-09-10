package com.finpro.grocery.users.dto;

import com.finpro.grocery.users.entity.User;
import lombok.Data;

@Data
public class ProfileDataDTO {
    private String name;
    private String email;
    private String profilePicture;
    private String referralCode;
    private Boolean isVerified;

    public static ProfileDataDTO toDto(User user){
        ProfileDataDTO profileDataDTO = new ProfileDataDTO();
        profileDataDTO.setName(user.getName());
        profileDataDTO.setEmail(user.getEmail());
        profileDataDTO.setProfilePicture(user.getProfilePicture());
        profileDataDTO.setReferralCode(user.getReferralCode());
        profileDataDTO.setIsVerified(user.getIsVerified());

        return profileDataDTO;
    }
}
