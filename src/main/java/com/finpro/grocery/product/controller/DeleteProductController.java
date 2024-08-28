package com.finpro.grocery.product.controller;

import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.service.DeleteProduct;
import com.finpro.grocery.share.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/products")
public class DeleteProductController {

  @Autowired
  private DeleteProduct productService;

  @DeleteMapping("/{id}")
  public ApiResponse<Product> removeProduct(@PathVariable Long id) {
    Product removedProduct = productService.removeProduct(id);
    return new ApiResponse<>("DELETED", "200", removedProduct);
  }

  @PutMapping("/{id}/restore")
  public ApiResponse<Product> restoreProduct(@PathVariable Long id) {
    Product restoredProduct = productService.restoreProduct(id);
    return new ApiResponse<>("OK", "200", restoredProduct);
  }

  @DeleteMapping("/{id}/images/{imageId}")
  public ApiResponse<Product> removeImageFromProduct(
    @PathVariable Long id, 
    @PathVariable Long imageId
  ) {
    Product removedImage = productService.removeImageFromProduct(id, imageId);
    return new ApiResponse<>("OK", "200", removedImage);
  }
  
  @DeleteMapping("/{id}/images/{imageId}/restore")
  public ApiResponse<Product> restoreImageFromProduct(
    @PathVariable Long id, 
    @PathVariable Long imageId
  ) {
    Product removedImage = productService.restoreImageToProduct(id, imageId);
    return new ApiResponse<>("OK", "200", removedImage);
  }
  
}