package com.finpro.grocery.admin.controller;

import com.finpro.grocery.admin.dto.request.AdminRequestDTO;
import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.service.UpdateAdmin;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class UpdateAdminController {
  
  @Autowired
  private UpdateAdmin admin;

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('SCOPE_SUPER')")
    public ApiResponse<AdminResponseDTO> updateAdmin(@PathVariable Long id, @RequestBody AdminRequestDTO adminDto) {
    return new ApiResponse<>("OK", "200", admin.update(id, adminDto));
  }

}
