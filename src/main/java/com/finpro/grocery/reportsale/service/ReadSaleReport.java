package com.finpro.grocery.reportsale.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.reportsale.dto.response.SaleHistoryDTO;
import com.finpro.grocery.reportsale.repository.SaleReportRepository;
import com.finpro.grocery.share.pagination.Pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReadSaleReport {
  
  @Autowired
  private SaleReportRepository repository;

  public Pagination<SaleHistoryDTO> getAll(String start, String end, Long storeId, int page, int size, String sortBy, String sortDir) {
    Instant startDate = start == null ? Instant.now().minus(10, ChronoUnit.YEARS) : Instant.parse(start);
    Instant endDate = end == null ? Instant.now().plus(1, ChronoUnit.DAYS) : Instant.parse(end);
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Order> reports = repository.getAll(startDate, endDate, storeId, pageable);
    Page<SaleHistoryDTO> saleDTO = reports.map(this::convertToDto);

    return new Pagination<>(
      saleDTO.getTotalPages(),
      saleDTO.getTotalElements(),
      saleDTO.isFirst(),
      saleDTO.isLast(),
      saleDTO.getContent()
    );
  }

  private SaleHistoryDTO convertToDto(Order saleHistory) {
    return SaleReportDTOConverter.convertToDTO(saleHistory);
  }

}
