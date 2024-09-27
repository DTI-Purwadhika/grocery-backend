package com.finpro.grocery.product.controller;

import com.finpro.grocery.product.dto.request.RequestProductDTO;
import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
import com.finpro.grocery.product.service.UpdateProduct;
import com.finpro.grocery.share.response.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class PutProductController {

  @Autowired
  private UpdateProduct productService;

  @PutMapping("/{id}")
  public ApiResponse<ResponseProductDetailDTO> updateProduct(
    @PathVariable Long id, 
    @Valid @RequestBody RequestProductDTO product
  ) {
    ResponseProductDetailDTO updatedProduct = productService.updateProduct(id, product);
    return new ApiResponse<>("OK", "200", updatedProduct);
  }

}