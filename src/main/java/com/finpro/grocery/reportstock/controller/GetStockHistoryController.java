package com.finpro.grocery.reportstock.controller;

import com.finpro.grocery.reportstock.dto.response.StockHistoryDTO;
import com.finpro.grocery.reportstock.dto.response.StockSummaryDTO;
import com.finpro.grocery.reportstock.service.ReadStockReport;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock-history")
public class GetStockHistoryController {
  
  @Autowired
  ReadStockReport report;

  @GetMapping
  public ApiResponse<Pagination<StockHistoryDTO>> getAll(
    @RequestParam(name = "start", required = false) String start,
    @RequestParam(name = "end", required = false) String end,
    @RequestParam(name = "productId", required = false) Long productId,
    @RequestParam(name = "storeId", required = false) Long storeId,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
    @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
  ) {
    Pagination<StockHistoryDTO> reports = report.getAll(start, end, productId, storeId, page, size, sortBy, sortDir);
    return new ApiResponse<>("OK", "200", reports);
  }

  @GetMapping("/{id}")
  public ApiResponse<StockHistoryDTO> getStockReport(@PathVariable Long id) {
    return new ApiResponse<>("OK", "200", report.getStockReport(id));
  }

  @GetMapping("/summary")
  public ApiResponse<StockSummaryDTO> getSummary(
    @RequestParam(name = "start", required = false) String start,
    @RequestParam(name = "end", required = false) String end,
    @RequestParam(name = "productId", required = false) Long productId,
    @RequestParam(name = "storeId", required = false) Long storeId
  ) {
    StockSummaryDTO reports = report.getSummary(start, end, storeId, productId);
    return new ApiResponse<>("OK", "200", reports);
  }

  @GetMapping("/summary/all")
  public ApiResponse<Pagination<StockSummaryDTO>> getAllSummary(
    @RequestParam(name = "start", required = false) String start,
    @RequestParam(name = "end", required = false) String end,
    @RequestParam(name = "productId", required = false) Long productId,
    @RequestParam(name = "storeId", required = false) Long storeId,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
    @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
  ) {
    Pagination<StockSummaryDTO> reports = report.getAllSummary(start, end, productId, storeId, page, size, sortBy, sortDir);
    return new ApiResponse<>("OK", "200", reports);
  }

}
