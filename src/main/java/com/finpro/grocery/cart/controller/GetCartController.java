package com.finpro.grocery.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.service.ReadCart;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class GetCartController {
  
  @Autowired
  private ReadCart cartService;

  @GetMapping("/user/{userId}")
  public ApiResponse<Pagination<GetCartResponse>> getCart(
    @PathVariable long userId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir
  ) {
    Pagination<GetCartResponse> carts = cartService.getCart(userId, page, size, sortBy, sortDir);
    return new ApiResponse<>("OK", "200", carts);
  }
}
