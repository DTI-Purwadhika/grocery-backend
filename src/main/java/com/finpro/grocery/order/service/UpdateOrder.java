package com.finpro.grocery.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.finpro.grocery.order.dto.request.PaymentDTO;
import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.order.entity.OrderStatus;
import com.finpro.grocery.order.repository.OrderRepository;

import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class UpdateOrder {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ReadOrder read;

  @Transactional
  public String UpdateStatus(Long id, String update, String status) {
    Order order = read.findOrder(id);
    
    switch (status) {
      case "upload_proof":
        order.setProofUrl(update);
        order.setStatus(OrderStatus.Menunggu_Konfirmasi_Pembayaran);
        break;
      case "confirm_payment":
        order.setStatus(OrderStatus.Diproses);
        break;
      case "ready_to_send":
        order.setStatus(OrderStatus.Siap_Dikirim);
        break;
      case "deliver":
        order.setStatus(OrderStatus.Dikirim);
        order.setResiNumber(update);
        break;
      case "cancel":
        order.setStatus(OrderStatus.Dibatalkan);
        break;
      case "confirm":
        order.setStatus(OrderStatus.Pesanan_Dikonfirmasi);
        break;
      default:
        order.setStatus(OrderStatus.Menunggu_Pembayaran);
        break;
    }

    orderRepository.save(order);

    return status + " order success";	
  }

  @Transactional
  public void verifyPayment(PaymentDTO callback){
    Order order = read.finOrderByCode(callback.getExternal_id());
    UpdateStatus(order.getId(), callback.getStatus(), "confirm_payment");
  }

  @Scheduled(fixedDelay = 3600000)
  public void updateStatus() {
    List<Order> unpaidOrders = orderRepository.findUnpaidOrders(OrderStatus.Menunggu_Pembayaran);
    for (Order order : unpaidOrders) {
      Instant now = Instant.now();
      Duration duration = Duration.between(order.getCreatedAt(), now);
      
      if (duration.toHours() >= 1)
        UpdateStatus(order.getId(), null, "cancel");
    }
  }

}
