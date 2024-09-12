package com.finpro.grocery.discount.controller;

import com.finpro.grocery.discount.dto.request.RequestDiscountDTO;
import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.service.UpdateDiscount;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discounts")
public class UpdateDiscountController {
  
  @Autowired
  private UpdateDiscount discountService;

  @PutMapping("/{id}")
    public ApiResponse<ResponseDiscountDTO> updateDiscount(@PathVariable Long id, @RequestBody RequestDiscountDTO discount) {
    ResponseDiscountDTO updatedDiscount = discountService.updateDiscount(id, discount);
    return new ApiResponse<>("OK", "200", updatedDiscount);
  }

}
