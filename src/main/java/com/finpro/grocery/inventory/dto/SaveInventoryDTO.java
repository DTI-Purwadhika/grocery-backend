package com.finpro.grocery.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveInventoryDTO {

  @NotNull(message = "Product is required")
  private Long productId;
  @NotNull(message = "Store is required")
  private Long storeId;
  @Min(value = 0, message = "Stock cannot be negative")
  private Long stock;

}
