package com.finpro.grocery.product.service;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.category.service.ReadCategory;
import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.service.CreateStock;
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

  @Autowired
  private CreateStock createStock;

  @Transactional
  public ResponseProductDetailDTO saveProduct(RequestProductDTO productDTO) {
    if(productRepository.existsByName(productDTO.getName()))
      throw new BadRequestException("Product with name " + productDTO.getName() + " already exists");
    
    Category category =  categoryService.getCategoryByName(productDTO.getCategory());
    
    Product product = DTOConverter.convertToProduct(productDTO, new Product(), category);
    product.setCode(sequenceService.generateUniqueCode("product_code_sequence", "PRD%06d"));	
    Product savedProduct = productRepository.save(product);

    System.out.println(productDTO);

    if(productDTO.getStocks() != null){
      productDTO.getStocks().forEach(stock -> {
        RequestInventoryDTO inventory = new RequestInventoryDTO();
        inventory.setProductId(savedProduct.getId());
        inventory.setStoreId(stock.getStoreId());
        inventory.setStock(stock.getStock());
        createStock.saveInventory(inventory);
      });
    }

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
