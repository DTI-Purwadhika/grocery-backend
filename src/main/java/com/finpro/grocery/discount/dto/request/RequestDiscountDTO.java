package com.finpro.grocery.discount.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDiscountDTO {
  private String name;
  private String description;
  private String type;
  private Double value;
  private Double minPurchaseAmount;
  private Double maxDiscountAmount;
  private Long storeId;
  private Long productId;  
}
