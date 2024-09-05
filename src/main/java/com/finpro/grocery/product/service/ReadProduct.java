package com.finpro.grocery.product.service;

import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.product.dto.response.ResponseProductDTO;
import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
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
  
  public Pagination<ResponseProductDTO> getAll(String keywordName, String keywordCode, String category, int page, int size, String sortBy, String sortDir) {
    if(category != null && category != "" && categoryService.getCategoryByName(category) == null) throw new ResourceNotFoundException("Category with name " + category + " not found");
    
    String name = keywordName == null ? "" : keywordName;
    String code = keywordCode == null ? "" : keywordCode;
    Long categoryId = category == null ? null : categoryService.getCategoryByName(category).getId();
    System.out.println(categoryId);
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Product> products =  productRepository.getAll(name, code, categoryId, pageable);
    Page<ResponseProductDTO> productDTOs = products.map(this::convertToDto);

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

  public ResponseProductDetailDTO getProduct(Long id) {
    return convertToDetailDto(getProductById(id));
  }

  private ResponseProductDTO convertToDto(Product product) {
    ResponseProductDTO getDto = new ResponseProductDTO();

    getDto.setId(product.getId());
    getDto.setName(product.getName());
    getDto.setCategory(product.getCategory().getName());
    getDto.setPrice(product.getPrice());
    getDto.setImages(product.getImages().get(0).getUrl());
    
    return getDto;
  }

  private ResponseProductDetailDTO convertToDetailDto(Product product) {
    ResponseProductDetailDTO getDto = new ResponseProductDetailDTO();

    getDto.setId(product.getId());
    getDto.setName(product.getName());
    getDto.setCategory(product.getCategory().getName());
    getDto.setPrice(product.getPrice());
    getDto.setImages(product.getImages());
    getDto.setDescription(product.getDescription());
    
    return getDto;
  }

}
