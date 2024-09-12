package com.finpro.grocery.category.service;

import com.finpro.grocery.category.dto.response.ResponseCategoryDTO;
import com.finpro.grocery.category.dto.response.ResponseCategoryListDTO;
import com.finpro.grocery.category.entity.Category;

class DTOConverter {
  
  static ResponseCategoryDTO convertToDto(Category category) {
    ResponseCategoryDTO response = new ResponseCategoryDTO();
    response.setId(category.getId());
    response.setName(category.getName());
    response.setDescription(category.getDescription());
    response.setTotalProduct(category.getProducts().size()); 

    return response;
  }

   static ResponseCategoryListDTO convertToDtoList(Category category) {
    ResponseCategoryListDTO response = new ResponseCategoryListDTO();
    response.setId(category.getId());
    response.setName(category.getName());
    response.setDescription(category.getDescription());
    response.setProducts(category.getProducts());
    return response;
  }

}
