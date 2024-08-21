package com.finpro.grocery.product.service;

import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.entity.ProductImage;
import com.finpro.grocery.product.repository.ProductImageRepository;
import com.finpro.grocery.product.repository.ProductRepository;
import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProduct {

  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private ProductImageRepository imageRepository;

  @Autowired
  private ReadProduct read;

  @Transactional
  public Product removeProduct(Long id) {
    Product product = read.getProductById(id);

    if(product.getDeletedAt() != null) throw new ResourceNotFoundException("Product with Code " + product.getCode() + " already deleted");

    product.setDeletedAt(Instant.now());
    product.setUpdatedAt(Instant.now());

    return productRepository.save(product);
  }

  @Transactional
  public Product restoreProduct(Long id) {
    Product product = read.getProductById(id);

    if(product.getDeletedAt() == null) throw new BadRequestException("Product with Code " + product.getCode() + " not yet deleted");

    product.setDeletedAt(null);
    product.setUpdatedAt(Instant.now());

    return productRepository.save(product);
  }

  @Transactional
  public Product removeImageFromProduct(Long id, Long imageId) {
    Product product = read.getProductById(id);

    ProductImage imageToRemove = imageRepository.findById(imageId)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Image with id: " + imageId));

    product.getImages().remove(imageToRemove);

    if(imageToRemove.getDeletedAt() != null) throw new ResourceNotFoundException("Image already removed");
    imageToRemove.setDeletedAt(Instant.now());
    
    imageRepository.save(imageToRemove);

    return productRepository.save(product);
  }

  @Transactional
  public Product restoreImageToProduct(Long id, Long imageId) {
    Product product = read.getProductById(id);

    ProductImage imageToRestore = imageRepository.findById(imageId)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Image with id: " + imageId));

    product.getImages().add(imageToRestore);

    if(imageToRestore.getDeletedAt() == null) throw new ResourceNotFoundException("Image not yet removed");
    imageToRestore.setDeletedAt(null);
    
    imageRepository.save(imageToRestore);

    return productRepository.save(product);
  }
}
