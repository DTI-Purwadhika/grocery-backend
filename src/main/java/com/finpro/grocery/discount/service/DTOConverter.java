package com.finpro.grocery.discount.service;

import java.time.Instant;

import com.finpro.grocery.discount.dto.request.RequestDiscountDTO;
import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.store.entity.Store;

class DTOConverter {
  static ResponseDiscountDTO convertToDto(Discount discount) {
    ResponseDiscountDTO response = new ResponseDiscountDTO();

    response.setId(discount.getId());
    response.setName(discount.getName());
    response.setDescription(discount.getDescription());
    response.setCode(discount.getCode());
    response.setValue(discount.getValue()); 
    response.setMinPurchaseAmount(discount.getMinPurchaseAmount()); 
    response.setMaxDiscountAmount(discount.getMaxDiscountAmount()); 
    response.setStoreName(discount.getStore().getName()); 
    response.setProductName(discount.getProduct().getName());

    return response;
  }

  static Discount convertToEntity(RequestDiscountDTO discount, Discount request, Store store, Product product) {
    if(!discount.getName().isBlank())
      request.setName(discount.getName());

    if(!discount.getDescription().isBlank()) 
      request.setDescription(discount.getDescription());
    
    if(discount.getValue() != null)
    request.setValue(discount.getValue());
    
    if(discount.getMinPurchaseAmount() != null)
    request.setMinPurchaseAmount(discount.getMinPurchaseAmount());
    
    if(discount.getMaxDiscountAmount() != null)
    request.setMaxDiscountAmount(discount.getMaxDiscountAmount());
    
    request.setStore(store);
    request.setProduct(product);
    request.setUpdatedAt(Instant.now());

    return request;
  }
}
