package com.finpro.grocery.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finpro.grocery.admin.dto.response.AdminResponseDTO;
import com.finpro.grocery.admin.dto.request.AdminRequestDTO;
import com.finpro.grocery.admin.repository.AdminRepository;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.StoreService;
import com.finpro.grocery.users.entity.User;

@Service
public class CreateAdmin {
  
  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private StoreService storeService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AdminResponseDTO save(AdminRequestDTO user) {
    Store store = storeService.getStoreById(user.getStoreId());
    User admin = new User();

    admin.setName(user.getName());
    admin.setEmail(user.getEmail());
    admin.setRole(User.UserRole.ADMIN);
    admin.setProfilePicture(user.getProfilePicture());
    admin.setIsVerified(true);
    admin.setPassword(passwordEncoder.encode(user.getPassword()));
    admin.setStore(store);

    return DTOConverter.convertToDto(adminRepository.save(admin));
  }

}

