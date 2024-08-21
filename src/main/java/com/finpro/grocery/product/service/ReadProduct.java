package com.finpro.grocery.product.service;

import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.product.dto.GetProductDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.repository.ProductRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReadProduct {

  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ReadCategory categoryService;
  
  public Pagination<GetProductDTO> getAll(String keywordName, String keywordCode, String category, int page, int size, String sortBy, String sortDir) {
    if(category != null && categoryService.getCategory(category) == null) throw new ResourceNotFoundException("Category with name " + category + " not found");
    
    String name = keywordName == null ? "" : keywordName;
    String code = keywordCode == null ? "" : keywordCode;
    Long categoryId = category == null ? null : categoryService.getCategory(category).getId();
    
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Product> products =  productRepository.getAll(name, code, categoryId, pageable);

    Page<GetProductDTO> productDTOs = products.map(this::convertToDto);

    return new Pagination<>(
      productDTOs.getTotalPages(),
      productDTOs.getTotalElements(),
      productDTOs.isFirst(),
      productDTOs.isLast(),
      productDTOs.getContent()
    );
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Product with id: " + id));
  }

  private GetProductDTO convertToDto(Product product) {
    GetProductDTO getDto = new GetProductDTO();
    getDto.setId(product.getId());
    getDto.setName(product.getName());
    getDto.setCategory(product.getCategory().getName());
    getDto.setPrice(product.getPrice());
    getDto.setImages(product.getImages().get(0).getUrl());
    return getDto;
  }

}
