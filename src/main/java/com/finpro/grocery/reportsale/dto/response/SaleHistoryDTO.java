package com.finpro.grocery.reportsale.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistoryDTO {

  private Long id;
  private Long user;
  private Long store;
  private String storeName;
  private String status;
  private String code;
  private String proofUrl;
  private String resiNumber;
  private BigDecimal totalAmount;
  private BigDecimal totalShipment;
  private BigDecimal totalDiscount;
  private BigDecimal totalPayment;
  private String purchaseDate;
  private String completedDate;

}
