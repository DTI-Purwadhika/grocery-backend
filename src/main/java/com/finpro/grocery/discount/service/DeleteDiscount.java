package com.finpro.grocery.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.repository.DiscountRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class DeleteDiscount {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private ReadDiscount read;

  @Transactional
  public Discount removeDiscount(Long id) {
    Discount discount = read.getDiscount(id);

    if(discount.getDeletedAt() != null) throw new ResourceNotFoundException("Discount with name " + discount.getName() + " already deleted");

    discount.setUpdatedAt(Instant.now());
    discount.setDeletedAt(Instant.now());

    return discountRepository.save(discount);
  }

  @Transactional
  public Discount restoreDiscount(Long id) {
    Discount discount = read.getDiscount(id);

    if(discount.getDeletedAt() == null) throw new ResourceNotFoundException("Discount with name " + discount.getName() + " not yet deleted");

    discount.setUpdatedAt(Instant.now());
    discount.setDeletedAt(null);

    return discountRepository.save(discount);
  }
  
}
