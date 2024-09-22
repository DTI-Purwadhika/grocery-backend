package com.finpro.grocery.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.order.dto.response.InvoiceDTO;
import com.finpro.grocery.order.service.CreateOrder;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/checkouts")
public class PostOrderController {

  @Autowired
  private CreateOrder order;
  
  @PostMapping("/{cartId}")
  public ApiResponse<InvoiceDTO> createOrder(@PathVariable Long cartId) {
    return new ApiResponse<>("OK", "200", order.save(cartId));
  }
  
}
