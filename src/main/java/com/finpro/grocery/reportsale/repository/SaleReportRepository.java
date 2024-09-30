package com.finpro.grocery.reportsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finpro.grocery.order.entity.Order;

import java.time.Instant;

public interface SaleReportRepository extends JpaRepository<Order, Long> {

  @Query("SELECT sh FROM Order sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND sh.createdAt BETWEEN :startDate and :endDate")
  Page<Order> getAll(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate, @Param("storeId") Long storeId, Pageable pageable);

}
