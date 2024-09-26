package com.finpro.grocery.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finpro.grocery.order.dto.request.PaymentDTO;
import com.finpro.grocery.order.service.UpdateOrder;
import com.finpro.grocery.share.response.ApiResponse;

@RestController
@RequestMapping("/api/v1/checkouts")
public class PutOrderController {

  @Autowired
  private UpdateOrder order;

  @PostMapping("/payment-webhook")
  public void handlePaymentCallback(@RequestBody PaymentDTO callback) {
    order.verifyPayment(callback);
  }

  @PutMapping("/{id}")
  public ApiResponse<String> uploadPaymentProof(@RequestParam("status") String status, @RequestParam("update") String update, @PathVariable("id") Long orderId) {
    return new ApiResponse<>("OK", "200", order.UpdateStatus(orderId, update, status));
  }

}
