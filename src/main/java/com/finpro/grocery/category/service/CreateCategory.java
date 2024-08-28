package com.finpro.grocery.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;
import com.finpro.grocery.share.exception.BadRequestException;

import jakarta.transaction.Transactional;

@Service
public class CreateCategory {

  @Autowired
  private CategoryRepository categoryRepository;

  @Transactional
  public Category saveCategory(Category category) {
    if(categoryRepository.isExist(category.getName())) throw new BadRequestException("Category already exist");
    return categoryRepository.save(category);
  }
  
}
