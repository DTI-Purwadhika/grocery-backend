package com.finpro.grocery.category.controller;

import com.finpro.grocery.category.dto.request.RequestCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.service.UpdateCategory;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class UpdateCategoryController {
  
  @Autowired
  private UpdateCategory category;

  @PutMapping("/{id}")
    public ApiResponse<ResponseCategoryDTO> updateCategory(@PathVariable Long id, @RequestBody RequestCategoryDTO categoryDto) {
    return new ApiResponse<>("OK", "200", category.update(id, categoryDto));
  }

}
