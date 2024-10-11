package com.finpro.grocery.cart.service;

import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
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
  @Autowired
  private UserRepository userRepository;

  public Cart getCartById(Long cartId) {
    return cartRepository.findById(cartId)
      .orElseThrow(() -> new ResourceNotFoundException("Cart with id: " + cartId + " not found"));
  }

  public GetCartResponse getCart(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Cart cart =  cartRepository.findByUser(user);
    
    return CartDTOConverter.convertToDto(cart);
  }

  public int getTotalWeight(Long userId){
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    Cart cart =  cartRepository.findByUser(user);
    return cart.getItems().stream().map(CartItem::getQuantity).mapToInt(Integer::intValue).sum() * 100;
  }

}
