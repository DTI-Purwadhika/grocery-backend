package com.finpro.grocery.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCategoryDTO {

  private String name;
  private String description;

}
