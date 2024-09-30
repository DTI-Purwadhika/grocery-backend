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
    System.out.println("uhuy");
    String name = keyword == null ? "" : keyword;
    User.UserRole userRole = null;
    
    if(!roleKeyword.isBlank())
      userRole = User.UserRole.valueOf(roleKeyword);
    
    System.out.println("ahay");
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);
    System.out.println("ehey");
    
    Page<User> admins =  adminRepository.getAll(name, userRole, pageable);
    System.out.println(admins);
    System.out.println("ohoy");
    
    Page<AdminResponseDTO> adminDto = admins.map(this::convertToDto);
    System.out.println(adminDto);
    
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
