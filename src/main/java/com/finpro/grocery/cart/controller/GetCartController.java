package com.finpro.grocery.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.service.ReadCart;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class GetCartController {
  
  @Autowired
  private ReadCart cart;

  @GetMapping("/user/{userId}")
  public ApiResponse<GetCartResponse> getCart(@PathVariable String userId) {
    return new ApiResponse<>("OK", "200", cart.getCart(userId));
  }
}
