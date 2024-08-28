package com.finpro.grocery.category.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class DeleteCategory {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ReadCategory read;

  @Transactional
  public Category removeCategory(String name) {
    Category category = read.getCategory(name);

    if(category.getDeletedAt() != null) throw new ResourceNotFoundException("Category with name " + name + " already deleted");

    category.setUpdatedAt(Instant.now());
    category.setDeletedAt(Instant.now());

    return categoryRepository.save(category);
  }

  @Transactional
  public Category restoreCategory(String name) {
    Category category = read.getCategory(name);

    if(category.getDeletedAt() == null) throw new ResourceNotFoundException("Category with name " + name + " not yet deleted");

    category.setUpdatedAt(Instant.now());
    category.setDeletedAt(null);

    return categoryRepository.save(category);
  }
}
