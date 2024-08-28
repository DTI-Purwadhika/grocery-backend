package com.finpro.grocery.category.dto.response;

import com.finpro.grocery.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCategoryListDTO {

  private String name;
  private String description;
  private List<Product> products;

}
