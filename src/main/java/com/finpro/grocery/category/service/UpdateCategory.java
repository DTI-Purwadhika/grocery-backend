package com.finpro.grocery.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.dto.request.RequestCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class UpdateCategory {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ReadCategory read;

  @Transactional
  public ResponseCategoryDTO updateCategory(String name, RequestCategoryDTO category) {
    Category updatedCategory = read.getCategory(name);

    if(!category.getName().isBlank())
    updatedCategory.setName(category.getName());
    
    if(!category.getDescription().isBlank())
    updatedCategory.setDescription(category.getDescription());
    
    updatedCategory.setUpdatedAt(Instant.now());
    categoryRepository.save(updatedCategory);
    ResponseCategoryDTO categoryDto = convertToDto(updatedCategory);

    return categoryDto;
  }

  private ResponseCategoryDTO convertToDto(Category category) {
    ResponseCategoryDTO getDto = new ResponseCategoryDTO();
    getDto.setName(category.getName());
    getDto.setDescription(category.getDescription());
    getDto.setTotalProduct(category.getProducts().size());
    return getDto;
  }
  
}
