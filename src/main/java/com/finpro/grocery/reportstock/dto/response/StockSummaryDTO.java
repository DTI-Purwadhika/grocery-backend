package com.finpro.grocery.reportstock.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSummaryDTO {

  private int totalAdditions;
  private int totalDeductions;
  private int finalStock;
  
}
