package com.finpro.grocery.cart.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {
  private Long productId;
  private Long storeId;
  @Min(value = 1, message = "Stock cannot be negative")
  private Integer quantity;
}
