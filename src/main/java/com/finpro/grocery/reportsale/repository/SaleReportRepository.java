package com.finpro.grocery.reportsale.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finpro.grocery.order.entity.Order;
import com.finpro.grocery.reportsale.dto.response.SaleSummaryDTO;

import java.time.Instant;

public interface SaleReportRepository extends JpaRepository<Order, Long> {

  @Query("SELECT sh FROM Order sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND  sh.changeDate BETWEEN :startDate and :endDate ORDER BY sh.changeDate DESC")
  Page<Order> getAll(@Param("starDate") Instant startDate, @Param("endDate") Instant endDate, @Param("storeId") Long storeId , Pageable pageable );

  @Query("SELECT sh.product.id as productId, SUM(sh.addition) as additions, SUM(sh.reduction) as deductions, sh.product.name as productName FROM Order sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND WHERE  sh.changeDate BETWEEN :startDate and :endDate GROUP BY sh.product.id")
  SaleSummaryDTO getSummary(@Param("starDate") Instant startDate, @Param("endDate") Instant endDate,@Param("storeId") Long storeId );

  @Query("SELECT sh.product.id as productId, SUM(sh.addition) as additions, SUM(sh.reduction) as deductions, sh.product.name as productName, sh.store.id as storeId, sh.store.name as storeName, FUNCTION('MONTH', sh.changeDate) as month FROM Order sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND sh.changeDate BETWEEN :startDate and :endDate GROUP BY FUNCTION('MONTH', sh.changeDate), sh.store.id, sh.product.id")
  Page<SaleSummaryDTO> getAllSummary(@Param("starDate") Instant startDate, @Param("endDate") Instant endDate, @Param("storeId") Long storeId , Pageable pageable );

}
