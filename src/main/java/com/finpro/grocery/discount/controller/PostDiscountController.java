package com.finpro.grocery.discount.controller;

import com.finpro.grocery.discount.dto.request.RequestDiscountDTO;
import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.service.CreateDiscount;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/promotions")
public class PostDiscountController {

  @Autowired
  private CreateDiscount discountService;

  @PostMapping
  public ApiResponse<ResponseDiscountDTO> saveDiscount(@RequestBody RequestDiscountDTO discount) {
    ResponseDiscountDTO savedDiscount = discountService.saveDiscount(discount);
    return new ApiResponse<>("OK", "200", savedDiscount);
  }

}
