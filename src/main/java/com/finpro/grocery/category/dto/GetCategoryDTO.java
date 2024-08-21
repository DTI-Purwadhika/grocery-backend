package com.finpro.grocery.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCategoryDTO {

  private String name;
  private String description;
  private Integer totalProduct;

}
