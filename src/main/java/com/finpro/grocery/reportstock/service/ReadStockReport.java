package com.finpro.grocery.reportstock.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.finpro.grocery.reportstock.dto.response.StockHistoryDTO;
import com.finpro.grocery.reportstock.dto.response.StockSummaryDTO;
import com.finpro.grocery.reportstock.entity.StockReport;
import com.finpro.grocery.reportstock.repository.StockReportRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReadStockReport {
  
  @Autowired
  private StockReportRepository repository;

  public Pagination<StockHistoryDTO> getAll(String start, String end, Long productId, Long storeId, int page, int size, String sortBy, String sortDir) {
    Instant startDate = start == null ? Instant.now().minus(10, ChronoUnit.YEARS) : Instant.parse(start);
    Instant endDate = end == null ? Instant.now().plus(1, ChronoUnit.DAYS) : Instant.parse(end);
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<StockReport> reports = repository.getAll(startDate, endDate, productId, storeId, pageable);
    Page<StockHistoryDTO> stockDTO = reports.map(this::convertToDto);

    return new Pagination<>(
      stockDTO.getTotalPages(),
      stockDTO.getTotalElements(),
      stockDTO.isFirst(),
      stockDTO.isLast(),
      stockDTO.getContent()
    );
  }

  public StockHistoryDTO getStockReport(Long id) {
    StockReport stock = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Stock Report Not Found"));
    return convertToDto(stock);
  }

  public StockSummaryDTO getSummary(String start, String end, Long storeId, Long productId) {
    Instant startDate = start == null ? null : Instant.parse(start);
    Instant endDate = end == null ? Instant.now().plus(1, ChronoUnit.DAYS) : Instant.parse(end);
    if(start == null){
      LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
      startDate = startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    return repository.getSummary(startDate, endDate, storeId, productId);
  }

  public Pagination<StockSummaryDTO> getAllSummary(String start, String end, Long productId, Long storeId, int page, int size, String sortBy, String sortDir) {
    Instant startDate = start == null ? Instant.now().minus(10, ChronoUnit.YEARS) : Instant.parse(start);
    Instant endDate = end == null ? Instant.now().plus(1, ChronoUnit.DAYS) : Instant.parse(end);
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<StockSummaryDTO> reports = repository.getAllSummary(startDate, endDate, productId, storeId, pageable);

    return new Pagination<>(
      reports.getTotalPages(),
      reports.getTotalElements(),
      reports.isFirst(),
      reports.isLast(),
      reports.getContent()
    );
  }

  private StockHistoryDTO convertToDto(StockReport stockHistory) {
    return StockReportDTOConverter.convertToDTO(stockHistory);
  }

}
