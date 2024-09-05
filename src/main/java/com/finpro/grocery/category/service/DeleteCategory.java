package com.finpro.grocery.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class DeleteCategory {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ReadCategory read;

  @Transactional
  public ResponseCategoryDTO removeCategory(Long id) {
    Category category = read.getCategory(id);

    if(category.getDeletedAt() != null) throw new ResourceNotFoundException("Category with name " + category.getName() + " already deleted");

    category.setUpdatedAt(Instant.now());
    category.setDeletedAt(Instant.now());
    categoryRepository.save(category);

    return ConvertToResponse(category);
  }

  @Transactional
  public ResponseCategoryDTO restoreCategory(Long id) {
    Category category = read.getCategory(id);

    if(category.getDeletedAt() == null) throw new ResourceNotFoundException("Category with name " + category.getName() + " not yet deleted");

    category.setUpdatedAt(Instant.now());
    category.setDeletedAt(null);
    categoryRepository.save(category);

    return ConvertToResponse(category);
  }
  
  private ResponseCategoryDTO ConvertToResponse(Category category) {
    ResponseCategoryDTO response = new ResponseCategoryDTO();
    response.setId(category.getId());
    response.setName(category.getName());
    response.setDescription(category.getDescription());
    response.setTotalProduct(category.getProducts().size()); 

    return response;
  }
}
