package com.finpro.grocery.product.service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.product.dto.request.RequestProductDTO;
import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.entity.ProductImage;
import com.finpro.grocery.product.repository.ProductRepository;
import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.share.sequence.service.SequenceService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProduct {

  @Autowired
  private ProductRepository productRepository;
  
  @Autowired
  private SequenceService sequenceService;

  @Autowired
  private ReadProduct read;

  @Autowired
  private ReadCategory categoryService;

  @Transactional
  public ResponseProductDetailDTO saveProduct(RequestProductDTO productDTO) {
    if(productRepository.existsByName(productDTO.getName()))
      throw new BadRequestException("Product with name " + productDTO.getName() + " already exists");
    
    Category category =  categoryService.getCategoryByName(productDTO.getCategory());
    
    Product product = DTOConverter.convertToProduct(productDTO, new Product(), category);
    product.setCode(sequenceService.generateUniqueCode("product_code_sequence", "PRD%05d"));	
    productRepository.save(product);

    return DTOConverter.convertToDTO(product);
  }

  @Transactional
  public ResponseProductDetailDTO addImageToProduct(Long id, ProductImage image) {
    Product product = read.getProductById(id);

    image.setProduct(product);
    product.getImages().add(image);
    productRepository.save(product);

    return DTOConverter.convertToDTO(product);
  }
  
}
