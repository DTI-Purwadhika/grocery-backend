package com.finpro.grocery.product.controller;

import com.finpro.grocery.product.dto.response.ResponseProductDTO;
import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class GetProductController {

  @Autowired
  private ReadProduct productService;

  @GetMapping
  public ApiResponse<Pagination<ResponseProductDTO>> getAll(
    @RequestParam(required = false) String keyword,
    @RequestParam(required = false) String code,
    @RequestParam(required = false) String category,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "name") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir
  ) {
    Pagination<ResponseProductDTO> products = productService.getAll(keyword, code, category, page, size, sortBy, sortDir);
    return new ApiResponse<>("OK", "200", products);
  }

  @GetMapping("/{id}")
  public ApiResponse<ResponseProductDetailDTO> getProduct(@PathVariable Long id) {
    ResponseProductDetailDTO product = productService.getProduct(id);
    return new ApiResponse<>("OK", "200", product);
  }

}