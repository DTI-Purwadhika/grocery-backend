package com.finpro.grocery.reportsale.controller;

import com.finpro.grocery.reportsale.dto.response.SaleHistoryDTO;
import com.finpro.grocery.reportsale.service.ReadSaleReport;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sale-history")
public class GetSaleHistoryController {
  
  @Autowired
  ReadSaleReport report;

  @GetMapping
  public ApiResponse<Pagination<SaleHistoryDTO>> getAll(
    @RequestParam(name = "start", required = false) String start,
    @RequestParam(name = "end", required = false) String end,
    @RequestParam(name = "storeId", required = false) Long storeId,
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
    @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
  ) {
    Pagination<SaleHistoryDTO> reports = report.getAll(start, end, storeId, page, size, sortBy, sortDir);
    return new ApiResponse<>("OK", "200", reports);
  }

  // @GetMapping("/summary")
  // public ApiResponse<SaleSummaryDTO> getSummary(
  //   @RequestParam(name = "start", required = false) String start,
  //   @RequestParam(name = "end", required = false) String end,
  //   @RequestParam(name = "storeId", required = false) Long storeId
  // ) {
  //   SaleSummaryDTO reports = report.getSummary(start, end, storeId);
  //   return new ApiResponse<>("OK", "200", reports);
  // }

  // @GetMapping("/summary/all")
  // public ApiResponse<Pagination<SaleSummaryDTO>> getAllSummary(
  //   @RequestParam(name = "start", required = false) String start,
  //   @RequestParam(name = "end", required = false) String end,
  //   @RequestParam(name = "storeId", required = false) Long storeId,
  //   @RequestParam(name = "page", defaultValue = "0") int page,
  //   @RequestParam(name = "size", defaultValue = "10") int size,
  //   @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
  //   @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
  // ) {
  //   Pagination<SaleSummaryDTO> reports = report.getAllSummary(start, end, storeId, page, size, sortBy, sortDir);
  //   return new ApiResponse<>("OK", "200", reports);
  // }

}
