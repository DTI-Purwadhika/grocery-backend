package com.finpro.grocery.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInventoryDTO {

  private Long id;
  private String code;
  private String productName;
  private String storeName;
  private Long totalStock;

}
