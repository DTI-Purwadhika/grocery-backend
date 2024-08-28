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

  @DeleteMapping("/{name}")
  public ApiResponse<ResponseCategoryDTO> removeCategory(@PathVariable String name) {
    ResponseCategoryDTO deletedCategory = categoryService.removeCategory(name);
    return new ApiResponse<>("DELETED", "200", deletedCategory);
  }

  @PutMapping("/{name}/restore")
  public ApiResponse<ResponseCategoryDTO> restoreCategory(@PathVariable String name) {
    ResponseCategoryDTO restoredCategory = categoryService.restoreCategory(name);
    return new ApiResponse<>("OK", "200", restoredCategory);
  }
  
}
