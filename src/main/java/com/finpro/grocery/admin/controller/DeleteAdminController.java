package com.finpro.grocery.admin.controller;

import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.service.DeleteAdmin;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class DeleteAdminController {

  @Autowired
  private DeleteAdmin admin;

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER')")
  public ApiResponse<AdminResponseDTO> removeAdmin(@PathVariable Long id) {
    return new ApiResponse<>("DELETED", "200", admin.remove(id));
  }

  @PutMapping("/{id}/restore")
  @PreAuthorize("hasAuthority('SCOPE_SUPER')")
  public ApiResponse<AdminResponseDTO> restoreAdmin(@PathVariable Long id) {
    return new ApiResponse<>("OK", "200", admin.restore(id));
  }
  
}
