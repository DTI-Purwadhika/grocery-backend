package com.finpro.grocery.category.controller;

import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.service.DeleteCategory;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class DeleteCategoryController {

  @Autowired
  private DeleteCategory categoryService;

  @DeleteMapping("/{id}")
  public ApiResponse<ResponseCategoryDTO> removeCategory(@PathVariable Long id) {
    ResponseCategoryDTO deletedCategory = categoryService.removeCategory(id);
    return new ApiResponse<>("DELETED", "200", deletedCategory);
  }

  @PutMapping("/{id}/restore")
  public ApiResponse<ResponseCategoryDTO> restoreCategory(@PathVariable Long id) {
    ResponseCategoryDTO restoredCategory = categoryService.restoreCategory(id);
    return new ApiResponse<>("OK", "200", restoredCategory);
  }
  
}
