package com.finpro.grocery.inventory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInventoryDTO {

  private Long id;
  private String code;
  private String name;
  private String storeName;
  private Long totalStock;

}
