package com.finpro.grocery.product.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductDTO {

  private Long id;
  private String name;
  private String category;
  private BigDecimal price;
  private String images;

}
