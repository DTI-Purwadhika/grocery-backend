package com.finpro.grocery.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.discount.dto.request.RequestDiscountDTO;
import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.repository.DiscountRepository;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.share.sequence.service.SequenceService;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.impl.StoreServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class CreateDiscount {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private StoreServiceImpl storeServiceImpl;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private SequenceService sequenceService;

  @Transactional
  public ResponseDiscountDTO saveDiscount(RequestDiscountDTO discount) {

    Store store = storeServiceImpl.getStoreById(discount.getStoreId());
    Product product = productService.getProductById(discount.getProductId());

    Discount newDiscount = DTOConverter.convertToEntity(discount, new Discount(), store, product);
    newDiscount.setCode(sequenceService.generateUniqueCode("discount_code_sequence", "PRD%06d"));	
    discountRepository.save(newDiscount);

    return DTOConverter.convertToDto(newDiscount);
  }
  
}
