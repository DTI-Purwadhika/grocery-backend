package com.finpro.grocery.users.dto;

import com.finpro.grocery.users.entity.User;
import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Boolean isVerified;

    public static CustomerResponseDTO toDto(User user){
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(user.getId());
        customerResponseDTO.setName(user.getName());
        customerResponseDTO.setEmail(user.getEmail());
        customerResponseDTO.setIsVerified(user.getIsVerified());

        return customerResponseDTO;
    }
}
