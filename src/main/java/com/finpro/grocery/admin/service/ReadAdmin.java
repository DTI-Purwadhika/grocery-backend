package com.finpro.grocery.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.repository.AdminRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.users.entity.User;

@Service
public class ReadAdmin {
  
  @Autowired
  private AdminRepository adminRepository;

  public User getAdminById(Long userId) {
    return adminRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));
  }

  public AdminResponseDTO getAdmin(Long userId) {
    return DTOConverter.convertToDto(getAdminById(userId));
  }

  public Pagination<AdminResponseDTO> getAll(String keyword, String roleKeyword, int page, int size, String sortBy, String sortDir) {
    String name = keyword == null ? "" : keyword;
    String role = roleKeyword == null ? "" : roleKeyword;

    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<User> admins =  adminRepository.getAll(name, role, pageable);

    Page<AdminResponseDTO> adminDto = admins.map(this::convertToDto);
    
    return new Pagination<>(
      adminDto.getTotalPages(),
      adminDto.getTotalElements(),
      adminDto.isFirst(),
      adminDto.isLast(),
      adminDto.getContent()
    );
  }

  private AdminResponseDTO convertToDto(User user) {
    return DTOConverter.convertToDto(user);
  }
  
}
