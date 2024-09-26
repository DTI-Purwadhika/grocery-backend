package com.finpro.grocery.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse {
  private Long id;
  private List<GetCartItem> items;
}