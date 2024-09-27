package com.finpro.grocery.discount.controller;

import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.service.DeleteDiscount;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/promotions")
public class DeleteDiscountController {

  @Autowired
  private DeleteDiscount discountService;

  @DeleteMapping("/{id}")
  public ApiResponse<ResponseDiscountDTO> removeDiscount(@PathVariable Long id) {
    ResponseDiscountDTO deletedDiscount = discountService.removeDiscount(id);
    return new ApiResponse<>("DELETED", "200", deletedDiscount);
  }

  @PutMapping("/{id}/restore")
  public ApiResponse<ResponseDiscountDTO> restoreDiscount(@PathVariable Long id) {
    ResponseDiscountDTO restoredDiscount = discountService.restoreDiscount(id);
    return new ApiResponse<>("OK", "200", restoredDiscount);
  }
  
}
