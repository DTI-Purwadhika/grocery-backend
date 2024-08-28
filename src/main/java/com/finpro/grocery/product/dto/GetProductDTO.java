package com.finpro.grocery.product.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDTO {

  private Long id;
  private String name;
  private String category;
  private BigDecimal price;
  private String images;

}
