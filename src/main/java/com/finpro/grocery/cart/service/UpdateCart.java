package com.finpro.grocery.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.cart.repository.CartItemRepository;
import com.finpro.grocery.cart.repository.CartRepository;
import com.finpro.grocery.inventory.service.ReadStock;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

@Service
public class UpdateCart {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private ReadCart read;

  @Autowired
  private ReadStock stockService;

  public GetCartResponse updateItemQuantity(Long cartId, Long productId, Integer quantity) {
    Cart cart = read.getCartById(cartId);

    CartItem cartItem = cart.getItems().stream()
      .filter(item -> item.getProduct().getId().equals(productId))
      .findFirst()
      .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));

    if (quantity > 0) {
      if(stockService.checkStockProduct(productId, quantity))
        cartItem.setQuantity(quantity);
      else
        throw new ResourceNotFoundException("Insufficient stock available for the updated quantity");
    } else {
      cart.removeItem(cartItem);
      cartItemRepository.delete(cartItem);
    }

    cartRepository.save(cart);
    return CartDTOConverter.convertToDto(cart);
  }

}
