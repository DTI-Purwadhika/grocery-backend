package com.finpro.grocery.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.dto.request.RequestCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;
import com.finpro.grocery.share.exception.BadRequestException;

import jakarta.transaction.Transactional;

@Service
public class CreateCategory {

  @Autowired
  private CategoryRepository categoryRepository;

  @Transactional
  public ResponseCategoryDTO save(RequestCategoryDTO category) {
    if(categoryRepository.isExist(category.getName())) throw new BadRequestException("Category already exist");

    Category newCategory = new Category();
    newCategory.setName(category.getName());
    newCategory.setDescription(category.getDescription());
    categoryRepository.save(newCategory);

    ResponseCategoryDTO response = DTOConverter.convertToDto(newCategory);

    return response;
  }
  
}
