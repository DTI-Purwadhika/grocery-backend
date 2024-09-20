package com.finpro.grocery.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.cart.repository.CartItemRepository;
import com.finpro.grocery.cart.repository.CartRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

@Service
public class DeleteCart {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private ReadCart read;

  public GetCartResponse removeItemFromCart(Long cartId, Long productId) {
    Cart cart = cartRepository.findById(cartId)
      .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

    CartItem cartItem = cart.getItems().stream()
      .filter(item -> item.getProduct().getId().equals(productId))
      .findFirst()
      .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

    cart.removeItem(cartItem);
    cartItemRepository.delete(cartItem);
    cartRepository.save(cart);

    return CartDTOConverter.convertToDto(cart);
  }

  public GetCartResponse clearCart(Long cartId) {
    Cart cart = read.getCart(cartId);

    cartItemRepository.deleteAll(cart.getItems());
    Cart updatedCart = cartRepository.save(cart);

    return CartDTOConverter.convertToDto(updatedCart);
  }

}
