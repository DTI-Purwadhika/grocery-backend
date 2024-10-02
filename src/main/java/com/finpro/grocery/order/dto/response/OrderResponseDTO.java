package com.finpro.grocery.order.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {

  private Long id;
  private Long user;
  private Long store;
  private String status;
  private String code;
  private String invoiceUrl;
  private String proofUrl;
  private String resiNumber;
  private String description;
  private BigDecimal totalAmount;
  private BigDecimal totalShipment;
  private BigDecimal totalDiscount;
  private BigDecimal totalPayment;
  private String expiryDate;
  private String createdAt;
  private List<OrderProductResponseDTO> items;
  
}
