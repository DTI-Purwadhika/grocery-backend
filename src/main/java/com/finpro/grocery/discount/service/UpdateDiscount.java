package com.finpro.grocery.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.repository.DiscountRepository;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class UpdateDiscount {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private ReadDiscount read;

  @Transactional
  public ResponseDiscountDTO updateDiscount(Long id, Discount discount) {
    Discount updatedDiscount = read.getDiscount(id);

    if(!discount.getName().isBlank())
    updatedDiscount.setName(discount.getName());
    
    if(!discount.getDescription().isBlank())
    updatedDiscount.setDescription(discount.getDescription());
    
    updatedDiscount.setUpdatedAt(Instant.now());
    discountRepository.save(updatedDiscount);
    ResponseDiscountDTO discountDto = convertToDto(updatedDiscount);

    return discountDto;
  }

  private ResponseDiscountDTO convertToDto(Discount discount) {
    ResponseDiscountDTO getDto = new ResponseDiscountDTO();
    getDto.setName(discount.getName());
    getDto.setDescription(discount.getDescription());
    
    return getDto;
  }
  
}
