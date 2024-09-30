package com.finpro.grocery.order.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xendit.Xendit;
import com.xendit.exception.XenditException;
import com.xendit.model.Invoice;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {

  @Value("${xendit.secret_api_key}")
  private String apiKey;
  
  @Value("${baseurl.frontend}")
  private String baseUrl;

  @PostConstruct
  public void init() {
    Xendit.Opt.setApiKey(apiKey);
  }

  public Invoice createInvoice(String code, Number amount, String payerEmail, String description) {
    try {
      Map<String, Object> params = new HashMap<>();
      
      params.put("external_id", code);
      params.put("amount", amount);
      params.put("payer_email", payerEmail);
      params.put("description", description);
      params.put("success_redirect_url", baseUrl+"/my-cart/checkout/payment-success");
      params.put("failure_redirect_url", baseUrl+"/my-cart/checkout/payment-failed");
      Invoice invoice = Invoice.create(params);

      return invoice;
    } catch (XenditException e) {
      e.printStackTrace();
      throw new RuntimeException("Invoice creation failed");
    }
  }

}
