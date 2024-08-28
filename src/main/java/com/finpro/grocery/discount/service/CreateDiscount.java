package com.finpro.grocery.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.repository.DiscountRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateDiscount {

  @Autowired
  private DiscountRepository discountRepository;

  @Transactional
  public Discount saveDiscount(Discount discount) {
    return discountRepository.save(discount);
  }
  
}
