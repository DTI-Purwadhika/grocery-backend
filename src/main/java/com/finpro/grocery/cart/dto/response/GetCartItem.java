package com.finpro.grocery.cart.dto.response;

import com.finpro.grocery.product.dto.response.ResponseProductDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItem {
  private ResponseProductDTO product;
  private Integer quantity;
}
