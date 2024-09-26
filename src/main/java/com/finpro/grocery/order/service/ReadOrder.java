package com.finpro.grocery.order.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.order.dto.response.OrderResponseDTO;
import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.order.repository.OrderRepository;
import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.share.pagination.Pagination;

@Service
public class ReadOrder {
  
  @Autowired
  private OrderRepository orderRepository;

  public Pagination<OrderResponseDTO> getAll(String keyword, Long userId, Long storeId, int page, int size, String sortBy, String sortDir, String startDate, String endDate) {
    String code = keyword == null ? "" : keyword;

    Instant start = startDate == null ? null : Instant.parse(startDate);
    Instant end = endDate == null ? null : Instant.parse(endDate);

    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Order> orders = orderRepository.getAll(code, userId, storeId, start, end, pageable);
    Page<OrderResponseDTO> orderList = orders.map(this::convertToDto);
    
    return new Pagination<>(
      orderList.getTotalPages(),
      orderList.getTotalElements(),
      orderList.isFirst(),
      orderList.isLast(),
      orderList.getContent()
    );
  }

  public OrderResponseDTO getOrder (Long orderId) {
    Order order = findOrder(orderId);
    return convertToDto(order);
  }

  public Order findOrder(Long orderId) {
    return orderRepository.findById(orderId)
      .orElseThrow(() -> new BadRequestException("Order not found"));
  }

  public Order finOrderByCode(String code) {
    return orderRepository.findByCode(code)
      .orElseThrow(() -> new BadRequestException("Order not found"));
  }

  private OrderResponseDTO convertToDto(Order order) {
    return InvoiceDTOConverter.convertToDTO(order);
  }

}
