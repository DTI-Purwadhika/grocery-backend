package com.finpro.grocery.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.finpro.grocery.share.pagination.Pagination;

import com.finpro.grocery.admin.service.ReadAdmin;
import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/admins")
public class getAdminController {
  
  @Autowired
  private ReadAdmin admin;

  @GetMapping
  public ApiResponse<Pagination<AdminResponseDTO>> getAll(
    @RequestParam(defaultValue = "") String keyword,
    @RequestParam(defaultValue = "") String roleKeyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir
  ) {
    return new ApiResponse<>("OK", "200", admin.getAll(keyword, roleKeyword, page, size, sortBy, sortDir));
  }

  @GetMapping("/{id}")
  public ApiResponse<AdminResponseDTO> getAdmin(@PathVariable Long id) {
    return new ApiResponse<>("OK", "200", admin.getAdmin(id));
  }
}
