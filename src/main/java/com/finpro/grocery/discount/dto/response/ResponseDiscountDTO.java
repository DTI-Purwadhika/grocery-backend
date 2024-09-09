package com.finpro.grocery.discount.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDiscountDTO {
  private Long id;
  private String code;
  private String name;
  private String description;
  private String type;
  private double value;
  private double minPurchaseAmount;
  private Double maxDiscountAmount;
  private String storeName;
  private String productName;  
}
