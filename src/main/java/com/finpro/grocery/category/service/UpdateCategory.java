package com.finpro.grocery.category.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.dto.GetCategoryDTO;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class UpdateCategory {
  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ReadCategory read;

  @Transactional
  public GetCategoryDTO updateCategory(String name, Category category) {
    Category updatedCategory = read.getCategory(name);

    if(!category.getName().isBlank())
    updatedCategory.setName(category.getName());
    
    if(!category.getDescription().isBlank())
    updatedCategory.setDescription(category.getDescription());
    
    updatedCategory.setUpdatedAt(Instant.now());
    categoryRepository.save(updatedCategory);
    GetCategoryDTO categoryDto = convertToDto(updatedCategory);

    return categoryDto;
  }

  private GetCategoryDTO convertToDto(Category category) {
    GetCategoryDTO getDto = new GetCategoryDTO();
    getDto.setName(category.getName());
    getDto.setDescription(category.getDescription());
    getDto.setTotalProduct(category.getProducts().size());
    return getDto;
  }
}
