package com.finpro.grocery.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {
  
  private Long id;
  private String name;
  private String email;
  private String role;
  private String profilePicture;
  private Boolean isVerified;

}
