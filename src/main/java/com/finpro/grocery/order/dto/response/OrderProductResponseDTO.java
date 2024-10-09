package com.finpro.grocery.order.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDTO {
  
  private Long id;
  private String name;
  private String image;
  private BigDecimal price;
  private Integer quantity;
  private BigDecimal totalPrice;
  
}
