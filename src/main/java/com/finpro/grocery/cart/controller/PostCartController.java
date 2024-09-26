package com.finpro.grocery.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.cart.dto.request.AddToCartRequest;
import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.service.CreateCart;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class PostCartController {
  
  @Autowired
  private CreateCart cart;

  @PostMapping
  public ApiResponse<GetCartResponse> addToCart (
    @RequestParam Long userId, 
    @RequestBody AddToCartRequest request
  ) {
    return new ApiResponse<>("OK", "200", cart.addToCart(userId, request));
  }

}
