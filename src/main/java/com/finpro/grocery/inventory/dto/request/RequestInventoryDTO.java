package com.finpro.grocery.inventory.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestInventoryDTO {

  private Long productId;
  private Long storeId;
  @Min(value = 0, message = "Stock cannot be negative")
  private Long stock;

}
