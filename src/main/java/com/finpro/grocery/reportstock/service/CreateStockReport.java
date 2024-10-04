package com.finpro.grocery.reportstock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.reportstock.dto.request.StockHistoryRequestDTO;
import com.finpro.grocery.reportstock.entity.StockReport;
import com.finpro.grocery.reportstock.repository.StockReportRepository;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.impl.StoreServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class CreateStockReport {
  
  @Autowired
  private StockReportRepository repository;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private StoreServiceImpl storeServiceImpl;

  @Transactional
  public void create(StockHistoryRequestDTO stockReport) {
    Product product = productService.getProductById(stockReport.getProductId());
    Store store = storeServiceImpl.getStoreById(stockReport.getStoreId());
    StockReport report = StockReportDTOConverter.convertToEntity(stockReport, product, store);

    repository.save(report);
  }
  
}
