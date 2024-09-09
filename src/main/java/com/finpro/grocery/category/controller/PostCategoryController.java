package com.finpro.grocery.category.controller;

import com.finpro.grocery.category.dto.request.RequestCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.service.CreateCategory;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class PostCategoryController {

  @Autowired
  private CreateCategory categoryService;

  @PostMapping
  public ApiResponse<ResponseCategoryDTO> saveCategory(@RequestBody RequestCategoryDTO category) {
    ResponseCategoryDTO savedCategory = categoryService.saveCategory(category);
    return new ApiResponse<>("OK", "200", savedCategory);
  }

}
