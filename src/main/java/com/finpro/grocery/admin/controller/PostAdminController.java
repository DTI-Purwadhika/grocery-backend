package com.finpro.grocery.admin.controller;

import com.finpro.grocery.admin.dto.request.AdminRequestDTO;
import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.service.CreateAdmin;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class PostAdminController {

  @Autowired
  private CreateAdmin admin;

  @PostMapping
  public ApiResponse<AdminResponseDTO> saveAdmin(@RequestBody AdminRequestDTO adminDto) {
    return new ApiResponse<>("OK", "200", admin.save(adminDto));
  }

}
