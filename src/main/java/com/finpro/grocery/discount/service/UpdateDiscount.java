package com.finpro.grocery.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.discount.dto.request.RequestDiscountDTO;
import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.repository.DiscountRepository;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.impl.StoreServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class UpdateDiscount {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private ReadDiscount read;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private StoreServiceImpl storeServiceImpl;

  @Transactional
  public ResponseDiscountDTO updateDiscount(Long id, RequestDiscountDTO discount) {

    Product product = productService.getProductById(discount.getProductId());
    Store store = storeServiceImpl.getStoreById(discount.getStoreId());

    Discount newDiscount = DTOConverter.convertToEntity(discount, read.getDiscount(id), store, product);
    discountRepository.save(newDiscount);

    return DTOConverter.convertToDto(newDiscount);
  }
  
}
