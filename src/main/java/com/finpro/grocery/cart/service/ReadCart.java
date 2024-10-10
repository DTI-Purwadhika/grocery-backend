package com.finpro.grocery.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.repository.CartRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

@Service
public class ReadCart {

  @Autowired
  private CartRepository cartRepository;

  public Cart getCartById(Long cartId) {
    return cartRepository.findById(cartId)
      .orElseThrow(() -> new ResourceNotFoundException("Cart with id: " + cartId + " not found"));
  }

  public GetCartResponse getCart(Long userId) {
    Cart cart =  cartRepository.findByUserId(userId).orElse(
      new Cart(userId, null)
    );
    
    return CartDTOConverter.convertToDto(cart);
  }

}
