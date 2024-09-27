package com.finpro.grocery.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

  private String external_id;
  private String payment_method;
  private String status;
  private int amount;
  private int paid_amount;
  private String bank_code;
  private String payer_email;
  private String paid_at;
  private String adjusted_received_amount;
  private int fees_paid_amount;
  private String updated;
  private String created;
  private String currency;
  private String payment_channel;

}
