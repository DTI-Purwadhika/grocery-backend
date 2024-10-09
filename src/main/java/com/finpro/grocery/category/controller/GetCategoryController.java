package com.finpro.grocery.category.controller;

import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryListDTO;
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
  private ReadCategory category;

  @GetMapping
  public ApiResponse<Pagination<ResponseCategoryDTO>> getAll(
    @RequestParam(required = false) String keyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "name") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir
  ) {
    return new ApiResponse<>("OK", "200", category.getAll(keyword, page, size, sortBy, sortDir));
  }

  @GetMapping("/{name}")
  public ApiResponse<ResponseCategoryListDTO> getCategoryList(@PathVariable String name) {
    if (name.matches("\\d+")) return getCategory(Long.parseLong(name));
    return new ApiResponse<>("OK", "200", category.getCategoryList(name));
  }

  private ApiResponse<ResponseCategoryListDTO> getCategory(Long id) {
    return new ApiResponse<>("OK", "200", category.getCategoryById(id));
  }


}
