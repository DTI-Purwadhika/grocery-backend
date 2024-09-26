package com.finpro.grocery.product.controller;

import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
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
  public ApiResponse<ResponseProductDetailDTO> removeProduct(@PathVariable Long id) {
    ResponseProductDetailDTO removedProduct = productService.removeProduct(id);
    return new ApiResponse<>("DELETED", "200", removedProduct);
  }

  @PutMapping("/{id}/restore")
  public ApiResponse<ResponseProductDetailDTO> restoreProduct(@PathVariable Long id) {
    ResponseProductDetailDTO restoredProduct = productService.restoreProduct(id);
    return new ApiResponse<>("OK", "200", restoredProduct);
  }

  @DeleteMapping("/{id}/images/{imageId}")
  public ApiResponse<ResponseProductDetailDTO> removeImageFromProduct(
    @PathVariable Long id, 
    @PathVariable Long imageId
  ) {
    ResponseProductDetailDTO removedImage = productService.removeImageFromProduct(id, imageId);
    return new ApiResponse<>("OK", "200", removedImage);
  }
  
  @DeleteMapping("/{id}/images/{imageId}/restore")
  public ApiResponse<ResponseProductDetailDTO> restoreImageFromProduct(
    @PathVariable Long id, 
    @PathVariable Long imageId
  ) {
    ResponseProductDetailDTO removedImage = productService.restoreImageToProduct(id, imageId);
    return new ApiResponse<>("OK", "200", removedImage);
  }
  
}