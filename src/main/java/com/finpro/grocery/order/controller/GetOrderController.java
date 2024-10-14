package com.finpro.grocery.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.order.dto.response.OrderResponseDTO;
import com.finpro.grocery.order.service.ReadOrder;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/checkouts")
public class GetOrderController {
  
  @Autowired
  private ReadOrder order;

  @GetMapping
  public ApiResponse<Pagination<OrderResponseDTO>> getAll(
    @RequestParam(required = false) String keyword,
    @RequestParam(required = false) String userId,
    @RequestParam(required = false) Long storeId,
    @RequestParam(required = false) String startDate,
    @RequestParam(required = false) String endDate,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy,
    @RequestParam(defaultValue = "desc") String sortDir
  ) {
    return new ApiResponse<>("OK", "200", order.getAll(keyword, userId, storeId, page, size, sortBy, sortDir, startDate, endDate));
  }

  @GetMapping("/{id}")
  public ApiResponse<OrderResponseDTO> getOrder(@PathVariable String id) {
    return new ApiResponse<>("OK", "200", order.getOrder(id));
  }
  
}
