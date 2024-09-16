package com.finpro.grocery.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItem {
  private Long productId;
  private Integer quantity;
}
