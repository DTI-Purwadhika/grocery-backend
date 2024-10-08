package com.finpro.grocery.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.service.UpdateCart;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class PutCartController {

  @Autowired
  private UpdateCart cart;

  @PutMapping("/{cartId}/update-item/{productId}")
  public ApiResponse<GetCartResponse> updateItemQuantity(
    @PathVariable Long cartId, 
    @PathVariable Long productId, 
    @RequestParam Integer quantity
  ) {
    return new ApiResponse<>("OK", "200", cart.updateItemQuantity(cartId, productId, quantity));
  }

}
