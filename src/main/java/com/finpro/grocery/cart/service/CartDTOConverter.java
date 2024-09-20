package com.finpro.grocery.cart.service;

import java.util.stream.Collectors;

import com.finpro.grocery.cart.dto.response.GetCartItem;
import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.product.service.DTOConverter;

class CartDTOConverter {

  static GetCartResponse convertToDto(Cart cart) {
    GetCartResponse response = new GetCartResponse();
    response.setId(cart.getId());
    response.setItems(cart.getItems().stream().map(item -> {
      GetCartItem cartItem = new GetCartItem();
      cartItem.setQuantity(item.getQuantity());
      cartItem.setProduct(DTOConverter.convertToDtoSum(item.getProduct()));
      return cartItem;
    }).collect(Collectors.toList()));
    
    return response;
  }
}
