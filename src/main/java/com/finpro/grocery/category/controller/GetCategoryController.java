package com.finpro.grocery.category.controller;

import com.finpro.grocery.category.dto.GetCategoryDTO;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class GetCategoryController {

  @Autowired
  private ReadCategory categoryService;

  @GetMapping
  public ApiResponse<Pagination<GetCategoryDTO>> getAll(
    @RequestParam(required = false) Boolean nameOnly,
    @RequestParam(required = false) String keyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "name") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir
  ) {
    return new ApiResponse<>("OK", "200", categoryService.getAll(keyword, page, size, sortBy, sortDir, nameOnly));
  }

  @GetMapping("/{name}")
  public ApiResponse<Category> getCategory(@PathVariable String name) {
    Category category = categoryService.getCategory(name);
    return new ApiResponse<>("OK", "200", category);
  }

}
