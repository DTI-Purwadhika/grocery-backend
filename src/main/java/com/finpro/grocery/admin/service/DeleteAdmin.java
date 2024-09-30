package com.finpro.grocery.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.repository.AdminRepository;
import com.finpro.grocery.users.entity.User;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class DeleteAdmin {

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private ReadAdmin read;

  @Transactional
  public AdminResponseDTO remove(Long userId) {
    User admin = read.getAdminById(userId);
    admin.setDeletedAt(Instant.now());
    
    return DTOConverter.convertToDto(adminRepository.save(admin));
  }

  @Transactional
  public AdminResponseDTO restore(Long userId) {
    User admin = read.getAdminById(userId);
    admin.setDeletedAt(null);
    
    return DTOConverter.convertToDto(adminRepository.save(admin));
  }
  
}
