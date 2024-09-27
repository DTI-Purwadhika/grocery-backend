package com.finpro.grocery.reportstock.service;

import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.reportstock.dto.request.StockHistoryRequestDTO;
import com.finpro.grocery.reportstock.dto.response.StockHistoryDTO;
import com.finpro.grocery.reportstock.dto.response.StockSummaryDTO;
import com.finpro.grocery.reportstock.entity.StockReport;
import com.finpro.grocery.store.entity.Store;

class StockReportDTOConverter {
  
  static StockHistoryDTO convertToDTO(StockReport stockReport) {
    StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
    stockHistoryDTO.setProductName(stockReport.getProduct().getName());
    stockHistoryDTO.setStoreName(stockReport.getStore().getName());
    stockHistoryDTO.setQuantity(stockReport.getAddition() - stockReport.getReduction());
    stockHistoryDTO.setDescription(stockReport.getDescription());
    stockHistoryDTO.setDate(stockReport.getCreatedAt().toString());
    return stockHistoryDTO;
  }

  static StockSummaryDTO convertToSummaryDTO(StockReport stockReport) {
    StockSummaryDTO stockSummaryDTO = new StockSummaryDTO();
    // stockSummaryDTO.setTotalAdditions(stockReport.getQuantityChange());
    // stockSummaryDTO.setTotalDeductions(stockReport.getQuantityChange());
    // stockSummaryDTO.setFinalStock(stockReport.getQuantity());
    return stockSummaryDTO;
  }

  static StockReport convertToEntity(StockHistoryRequestDTO stockHistoryDTO, Product product, Store store) {
    StockReport stockReport = new StockReport();

    stockReport.setProduct(product);
    stockReport.setStore(store);
    stockReport.setDescription(stockHistoryDTO.getDescription());

    if(stockHistoryDTO.getQuantity() >= 0) 
      stockReport.setAddition(stockHistoryDTO.getQuantity());
    else 
      stockReport.setReduction(Math.abs(stockHistoryDTO.getQuantity()));

    return stockReport;
  }
  
}
