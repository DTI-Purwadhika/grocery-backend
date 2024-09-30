package com.finpro.grocery.reportstock.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryRequestDTO {

  private Long productId;
  private Long storeId;
  private Integer quantity;
  private String description;

}
