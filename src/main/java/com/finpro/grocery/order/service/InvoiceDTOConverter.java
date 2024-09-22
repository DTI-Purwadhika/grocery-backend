package com.finpro.grocery.order.service;

import com.finpro.grocery.order.dto.response.InvoiceDTO;
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
}
