package com.finpro.grocery.reportstock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockHistoryDTO {

  private String productName;
  private String storeName;
  private Integer quantity;
  private String description;
  private String date;

}
