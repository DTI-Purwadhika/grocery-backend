package com.finpro.grocery.cart.service;

import java.util.stream.Collectors;

import com.finpro.grocery.cart.dto.response.GetCartItem;
import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;

class DTOConverter {

  static GetCartResponse convertToDto(Cart cart) {
    GetCartResponse response = new GetCartResponse();

    response.setStoreName(cart.getStore().getName());
    response.setItems(cart.getItems().stream().map(item -> {
      GetCartItem cartItem = new GetCartItem();
      cartItem.setProductId(item.getProduct().getId());
      cartItem.setQuantity(item.getQuantity());
      return cartItem;
    }).collect(Collectors.toList()));
    
    return response;
  }
}
