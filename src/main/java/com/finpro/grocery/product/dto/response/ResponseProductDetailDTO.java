package com.finpro.grocery.product.dto.response;

import com.finpro.grocery.product.dto.request.RequestProductImage;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseProductDetailDTO {

  private Long id;
  private String name;
  private String category;
  private String description;
  private BigDecimal price;
  private List<RequestProductImage> images;

}
