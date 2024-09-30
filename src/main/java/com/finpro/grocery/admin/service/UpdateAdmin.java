package com.finpro.grocery.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.dto.request.AdminRequestDTO;
import com.finpro.grocery.admin.repository.AdminRepository;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.StoreService;
import com.finpro.grocery.users.entity.User;

import jakarta.transaction.Transactional;

@Service
public class UpdateAdmin {
  
  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private ReadAdmin read;

  @Autowired
  private StoreService storeService;

  @Transactional
  public AdminResponseDTO update(Long userId, AdminRequestDTO user) {
    Store store = storeService.getStoreById(user.getStoreId());
    User admin = read.getAdminById(userId);
    
    if(!user.getName().isBlank())
      admin.setName(user.getName());

    if(!user.getEmail().isBlank())
      admin.setEmail(user.getEmail());
    
    if(!user.getProfilePicture().isBlank())
      admin.setProfilePicture(user.getProfilePicture());

    if(user.getStoreId() != null)
      admin.setStore(store);

    return DTOConverter.convertToDto(adminRepository.save(admin));
  }
}
