package com.finpro.grocery.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
  private String id;
  private String code;
  private Number amount;
  private String description;
  private String status;
  private String invoiceUrl;
  private String expiryDate;
  private String createdAt;
}
