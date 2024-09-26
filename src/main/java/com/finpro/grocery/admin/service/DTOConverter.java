package com.finpro.grocery.admin.service;

import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.users.entity.User;

class DTOConverter {
  
  static AdminResponseDTO convertToDto(User user) {
    AdminResponseDTO response = new AdminResponseDTO();
    response.setId(user.getId());
    response.setName(user.getName());
    response.setEmail(user.getEmail());
    response.setRole(user.getRole().toString());
    response.setProfilePicture(user.getProfilePicture());
    response.setIsVerified(user.getIsVerified());
    
    return response;
  }

}
