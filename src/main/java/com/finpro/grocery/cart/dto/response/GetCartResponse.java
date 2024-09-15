package com.finpro.grocery.cart.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse {
  private List<GetCartItem> items;
  private String storeName;
}