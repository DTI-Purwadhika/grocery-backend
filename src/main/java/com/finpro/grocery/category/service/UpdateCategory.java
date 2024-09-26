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
  public ResponseCategoryDTO update(Long id, RequestCategoryDTO category) {
    Category updatedCategory = read.getCategory(id);

    if(!category.getName().isBlank())
    updatedCategory.setName(category.getName());
    
    if(!category.getDescription().isBlank())
    updatedCategory.setDescription(category.getDescription());
    
    updatedCategory.setUpdatedAt(Instant.now());
    categoryRepository.save(updatedCategory);
    ResponseCategoryDTO categoryDto = DTOConverter.convertToDto(updatedCategory);

    return categoryDto;
  }

}
