package com.finpro.grocery.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryListDTO;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.repository.CategoryRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;

@Service
public class ReadCategory {

  @Autowired
  private CategoryRepository categoryRepository;

  public Pagination<ResponseCategoryDTO> getAll(String keyword, int page, int size, String sortBy, String sortDir) {
    String name = keyword == null ? "" : keyword;

    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Category> categories = categoryRepository.getAll(name, pageable);
    Page<ResponseCategoryDTO> categoryDto = categories.map(this::convertToDto);
    
    return new Pagination<>(
      categoryDto.getTotalPages(),
      categoryDto.getTotalElements(),
      categoryDto.isFirst(),
      categoryDto.isLast(),
      categoryDto.getContent()
    );
  }

  public ResponseCategoryListDTO getCategoryById(Long id) {
    return convertToDtoList(getCategory(id));
  }

  public Category getCategory(Long id) {
    return categoryRepository.findById(id)
    .orElseThrow(() -> new ResourceNotFoundException("There's no Category with id: " + id));
  }

  public Category getCategoryByName(String name) {
    return categoryRepository.getByName(name)
    .orElseThrow(() -> new ResourceNotFoundException("There's no Category with name: " + name));
  }

  public ResponseCategoryListDTO getCategoryList(String name) {
    return convertToDtoList(getCategoryByName(name));
  }

  private ResponseCategoryDTO convertToDto(Category category) {
    ResponseCategoryDTO getDto = new ResponseCategoryDTO();
    getDto.setId(category.getId());
    getDto.setName(category.getName());
    getDto.setDescription(category.getDescription());
    getDto.setTotalProduct(category.getProducts().size());
    return getDto;
  }

  private ResponseCategoryListDTO convertToDtoList(Category category) {
    ResponseCategoryListDTO getDto = new ResponseCategoryListDTO();
    getDto.setId(category.getId());
    getDto.setName(category.getName());
    getDto.setDescription(category.getDescription());
    getDto.setProducts(category.getProducts());
    return getDto;
  }
  
}
