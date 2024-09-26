package com.finpro.grocery.order.service;

import com.finpro.grocery.order.dto.response.InvoiceDTO;
import com.finpro.grocery.order.dto.response.OrderResponseDTO;
import com.finpro.grocery.order.entity.Order;
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
    return response;
  }

}
