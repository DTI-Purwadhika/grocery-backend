package com.finpro.grocery.users.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProfileDTO {
    String name;
    String email;
    String password;
    MultipartFile profilePicture;
}
