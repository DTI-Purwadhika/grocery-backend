package com.finpro.grocery.discount.controller;

import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.service.DeleteDiscount;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discounts")
public class DeleteDiscountController {

  @Autowired
  private DeleteDiscount discountService;

  @DeleteMapping("/{id}")
  public ApiResponse<Discount> removeDiscount(@PathVariable Long name) {
    Discount deletedDiscount = discountService.removeDiscount(name);
    return new ApiResponse<>("DELETED", "200", deletedDiscount);
  }

  @PutMapping("/{id}/restore")
  public ApiResponse<Discount> restoreDiscount(@PathVariable Long name) {
    Discount restoredDiscount = discountService.restoreDiscount(name);
    return new ApiResponse<>("OK", "200", restoredDiscount);
  }
  
}
