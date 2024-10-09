package com.finpro.grocery.order.service;

import java.util.ArrayList;
import java.util.List;

import com.finpro.grocery.order.dto.response.InvoiceDTO;
import com.finpro.grocery.order.dto.response.OrderProductResponseDTO;
import com.finpro.grocery.order.dto.response.OrderResponseDTO;
import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.order.entity.OrderProduct;
import com.finpro.grocery.product.entity.ProductImage;
import com.xendit.model.Invoice;

class InvoiceDTOConverter {
  static InvoiceDTO convertToDTO(Invoice invoice) {
    InvoiceDTO response = new InvoiceDTO();
    response.setId(invoice.getId());
    response.setCode(invoice.getExternalId());
    response.setAmount(invoice.getAmount());
    response.setDescription(invoice.getDescription());
    response.setStatus(invoice.getStatus());
    response.setInvoiceUrl(invoice.getInvoiceUrl());
    response.setExpiryDate(invoice.getExpiryDate());
    response.setCreatedAt(invoice.getCreated());
    return response;
  }

  static OrderResponseDTO convertToDTO(Order order) {
    OrderResponseDTO response = new OrderResponseDTO();
    response.setId(order.getId());
    response.setCode(order.getCode());
    response.setInvoiceUrl(order.getInvoiceUrl());
    response.setProofUrl(order.getProofUrl());
    response.setResiNumber(order.getResiNumber());
    response.setDescription(order.getDescription());
    response.setTotalAmount(order.getTotalAmount());
    response.setTotalShipment(order.getTotalShipment());
    response.setTotalDiscount(order.getTotalDiscount());
    response.setTotalPayment(order.getTotalPayment());
    response.setExpiryDate(order.getExpiryDate());
    response.setCreatedAt(order.getCreatedAt().toString());
    response.setStatus(order.getStatus().toString());
    response.setUser(order.getUser());
    response.setItems(new ArrayList<>());

    for (OrderProduct orderProduct : order.getItems()) {
      OrderProductResponseDTO orderProductResponseDTO = new OrderProductResponseDTO();
      List<ProductImage> images = orderProduct.getInventory().getProduct().getImages();
      if (!images.isEmpty()) 
        orderProductResponseDTO.setImage(images.get(0).getUrl());
      else 
        orderProductResponseDTO.setImage(null);
      orderProductResponseDTO.setId(orderProduct.getId());
      orderProductResponseDTO.setName(orderProduct.getInventory().getProduct().getName());
      orderProductResponseDTO.setPrice(orderProduct.getInventory().getProduct().getPrice());
      orderProductResponseDTO.setQuantity(orderProduct.getQuantity());
      orderProductResponseDTO.setTotalPrice(orderProduct.getTotalPrice());
      response.getItems().add(orderProductResponseDTO);
    }

    return response;
  }

}
