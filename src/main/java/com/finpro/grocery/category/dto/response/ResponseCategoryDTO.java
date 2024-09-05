package com.finpro.grocery.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCategoryDTO {

  private Long id;
  private String name;
  private String description;
  private Integer totalProduct;

}
