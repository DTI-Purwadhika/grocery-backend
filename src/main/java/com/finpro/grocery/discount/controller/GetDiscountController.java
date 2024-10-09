package com.finpro.grocery.discount.controller;

import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.service.ReadDiscount;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/promotions")
public class GetDiscountController {

  @Autowired
  private ReadDiscount discountService;

  @GetMapping
  public ApiResponse<Pagination<ResponseDiscountDTO>> getAll(
    @RequestParam(required = false) Boolean nameOnly,
    @RequestParam(required = false) String keyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "name") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir
  ) {
    return new ApiResponse<>("OK", "200", discountService.getAll(keyword, page, size, sortBy, sortDir));
  }

  @GetMapping("/{id}")
  public ApiResponse<ResponseDiscountDTO> getDiscount(@PathVariable Long id) {
    ResponseDiscountDTO discount = discountService.getDiscountDetail(id);
    return new ApiResponse<>("OK", "200", discount);
  }

}
