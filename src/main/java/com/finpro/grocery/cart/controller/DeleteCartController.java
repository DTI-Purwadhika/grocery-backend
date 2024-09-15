package com.finpro.grocery.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.service.DeleteCart;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class DeleteCartController {

  @Autowired
  DeleteCart cartService;
  
  @DeleteMapping("/{cartId}/remove-item/{productId}")
  public ApiResponse<GetCartResponse> removeItem(
    @PathVariable Long cartId, 
    @PathVariable Long productId
  ) {
    GetCartResponse cart = cartService.removeItemFromCart(cartId, productId);
    return new ApiResponse<>("OK", "200", cart);
  }

  @DeleteMapping("/{cartId}/clear")
  public ApiResponse<GetCartResponse> clearCart(
    @PathVariable Long cartId
  ) {
    GetCartResponse cart = cartService.clearCart(cartId);
    return new ApiResponse<>("OK", "200", cart);
  }

}
