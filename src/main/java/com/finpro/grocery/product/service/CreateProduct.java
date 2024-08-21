package com.finpro.grocery.product.service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.product.dto.SaveProductDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.entity.ProductImage;
import com.finpro.grocery.product.repository.ProductRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.sequence.service.SequenceService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProduct {

  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ReadCategory categoryService;

  @Autowired
  private SequenceService sequenceService;

  @Autowired
  private ReadProduct read;

  @Transactional
  public Product saveProduct(SaveProductDTO productDTO) {
    Product product = dtoToProduct(productDTO, new Product());
    product.setCode(sequenceService.generateUniqueCode("product_code_sequence", "PRD%05d"));	
    
    return productRepository.save(product);
  }

  @Transactional
  public Product addImageToProduct(Long id, ProductImage image) {
    Product product = read.getProductById(id);

    image.setProduct(product);
    product.getImages().add(image);

    return productRepository.save(product);
  }
  
  private Product dtoToProduct(SaveProductDTO productDTO, Product product) {
    Category category = categoryService.getCategory(productDTO.getCategory());
    if(category == null) throw new ResourceNotFoundException("Category with name " + productDTO.getCategory() + " not found");
    
    product.setCategory(category);
    product.setName(productDTO.getName());
    product.setPrice(productDTO.getPrice());
    product.setDescription(productDTO.getDescription());
    product.getImages().clear();
    
    for (ProductImage image : productDTO.getImages()) {
      image.setProduct(product);
      product.getImages().add(image);
    }
    
    return product;
  }
  
}
