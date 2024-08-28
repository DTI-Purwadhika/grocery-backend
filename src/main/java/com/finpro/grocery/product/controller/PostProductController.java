package com.finpro.grocery.product.controller;

import com.finpro.grocery.product.dto.SaveProductDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.entity.ProductImage;
import com.finpro.grocery.product.service.CreateProduct;
import com.finpro.grocery.share.response.ApiResponse;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class PostProductController {

  @Autowired
  private CreateProduct productService;

  @PostMapping
  public ApiResponse<Product> saveProduct(@Valid @RequestBody SaveProductDTO product) {
    Product savedProduct = productService.saveProduct(product);
    return new ApiResponse<>("CREATED", "201", savedProduct);
  }

  @PostMapping("/{id}/images")
  public ApiResponse<Product> addImageToProduct(
    @PathVariable Long id, 
    @Valid @RequestBody ProductImage image
  ) {
    Product addedImage = productService.addImageToProduct(id, image);
    return new ApiResponse<>("OK", "200", addedImage);
  }

}