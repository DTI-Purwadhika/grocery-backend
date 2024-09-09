package com.finpro.grocery.product.service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.product.dto.request.RequestProductDTO;
import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.repository.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProduct {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ReadProduct read;

  @Autowired
  private ReadCategory categoryService;

  @Transactional
  public ResponseProductDetailDTO updateProduct(Long id, RequestProductDTO productDTO) {
    Product existingProduct = read.getProductById(id);

    Category category = new Category();
    if(!productDTO.getCategory().isBlank())
      category = categoryService.getCategoryByName(productDTO.getCategory());

    Product product = DTOConverter.convertToProduct(productDTO, existingProduct, category);
    productRepository.save(product);

    return DTOConverter.convertToDTO(product);
  }

}
