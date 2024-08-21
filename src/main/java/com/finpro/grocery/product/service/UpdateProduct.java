package com.finpro.grocery.product.service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.product.dto.SaveProductDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.entity.ProductImage;
import com.finpro.grocery.product.repository.ProductRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProduct {

  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ReadCategory categoryService;

  @Autowired
  private ReadProduct read;


  @Transactional
  public Product updateProduct(Long id, SaveProductDTO productDTO) {
    Product existingProduct = read.getProductById(id);
    Product product = dtoToProduct(productDTO, existingProduct);
    return productRepository.save(product);
  }
  
  private Product dtoToProduct(SaveProductDTO productDTO, Product product) {
    if(!productDTO.getCategory().isBlank()) {
      Category category = categoryService.getCategory(productDTO.getCategory());
      if(category == null) throw new ResourceNotFoundException("Category with name " + productDTO.getCategory() + " not found");
      product.setCategory(category);
    }

    if(!productDTO.getName().isBlank())
      product.setName(productDTO.getName());

    if(productDTO.getPrice() != null)
      product.setPrice(productDTO.getPrice());

    if(!productDTO.getDescription().isBlank())
      product.setDescription(productDTO.getDescription());

    if(productDTO.getImages() != null) {
      product.getImages().clear();
      for (ProductImage image : productDTO.getImages()) {
        image.setProduct(product);
        product.getImages().add(image);
      }
    }

    product.setUpdatedAt(Instant.now());
    return product;
  }

}
