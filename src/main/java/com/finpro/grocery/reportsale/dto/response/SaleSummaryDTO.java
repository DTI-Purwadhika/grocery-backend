package com.finpro.grocery.reportsale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleSummaryDTO {

  private int totalSale;
  private int totalIncome;
  
}
