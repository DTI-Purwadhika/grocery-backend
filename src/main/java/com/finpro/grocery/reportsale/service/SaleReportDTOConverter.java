package com.finpro.grocery.reportsale.service;

import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.reportsale.dto.response.SaleHistoryDTO;

class SaleReportDTOConverter {
  
  static SaleHistoryDTO convertToDTO(Order saleReport) {
    SaleHistoryDTO saleHistoryDTO = new SaleHistoryDTO();
    
    saleHistoryDTO.setId(saleReport.getId());
    saleHistoryDTO.setUser(saleReport.getUser());
    saleHistoryDTO.setStore(saleReport.getStore().getId());
    saleHistoryDTO.setStoreName(saleReport.getStore().getName());
    saleHistoryDTO.setStatus(saleReport.getStatus().toString());
    saleHistoryDTO.setCode(saleReport.getCode());
    saleHistoryDTO.setProofUrl(saleReport.getProofUrl());
    saleHistoryDTO.setResiNumber(saleReport.getResiNumber());
    saleHistoryDTO.setTotalAmount(saleReport.getTotalAmount());
    saleHistoryDTO.setTotalShipment(saleReport.getTotalShipment());
    saleHistoryDTO.setTotalDiscount(saleReport.getTotalDiscount());
    saleHistoryDTO.setTotalPayment(saleReport.getTotalPayment());
    saleHistoryDTO.setPurchaseDate(saleReport.getCreatedAt().toString());
    saleHistoryDTO.setCompletedDate(saleReport.getUpdatedAt().toString());

    return saleHistoryDTO;
  }
  
}
