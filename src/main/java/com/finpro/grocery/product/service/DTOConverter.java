package com.finpro.grocery.product.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.product.dto.request.RequestProductDTO;
import com.finpro.grocery.product.dto.request.RequestProductImage;
import com.finpro.grocery.product.dto.response.ResponseProductDTO;
import com.finpro.grocery.product.dto.response.ResponseProductDetailDTO;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.entity.ProductImage;

public class DTOConverter {

  static ResponseProductDetailDTO convertToDTO(Product product) {
    ResponseProductDetailDTO response = new ResponseProductDetailDTO();
    List<RequestProductImage> image = new ArrayList<>();
    List<RequestInventoryDTO> stocks = new ArrayList<>();

    for (ProductImage productImage : product.getImages()) {
      RequestProductImage img = new RequestProductImage();
      img.setUrl(productImage.getUrl());
      img.setAltText(productImage.getAltText());
      image.add(img);
    }

    for (Inventory inventory : product.getInventory()) {
      RequestInventoryDTO stock = new RequestInventoryDTO();
      stock.setStoreId(inventory.getStore().getId());
      stock.setStoreName(inventory.getStore().getName());
      stock.setStock(inventory.getStock());
      stock.setProductId(product.getId());
      stocks.add(stock);
    }
    
    response.setId(product.getId());
    response.setName(product.getName());
    response.setCategory(product.getCategory().getName());
    response.setPrice(product.getPrice());
    response.setImages(image);
    response.setStocks(stocks);
    response.setDescription(product.getDescription());

    return response;
  }

  public static ResponseProductDTO convertToDtoSum(Product product) {
    ResponseProductDTO getDto = new ResponseProductDTO();
    
    getDto.setId(product.getId());
    getDto.setName(product.getName());
    getDto.setCategory(product.getCategory().getName());
    getDto.setPrice(product.getPrice());
    if (product.getImages().isEmpty()) {
      getDto.setImages(null);
    } else {
      getDto.setImages(product.getImages().get(0).getUrl());
    }
    
    return getDto;
  }
  
  static Product convertToProduct(RequestProductDTO productDTO, Product request, Category category) {
    if(category != null)
      request.setCategory(category);

    if(!productDTO.getName().isBlank())
      request.setName(productDTO.getName());

    if(productDTO.getPrice() != null)
      request.setPrice(productDTO.getPrice());

    if(!productDTO.getDescription().isBlank())
      request.setDescription(productDTO.getDescription());
    
    if(productDTO.getImages() != null) {
      request.getImages().clear();
      for (RequestProductImage image : productDTO.getImages()) {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(request);
        productImage.setUrl(image.getUrl());
        productImage.setAltText(image.getAltText());
        request.getImages().add(productImage);
      }
    }

    request.setUpdatedAt(Instant.now());
    return request;
  }

}