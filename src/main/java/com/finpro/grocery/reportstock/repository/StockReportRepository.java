package com.finpro.grocery.reportstock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finpro.grocery.reportstock.dto.response.StockSummaryDTO;
import com.finpro.grocery.reportstock.entity.StockReport;

import java.time.Instant;

public interface StockReportRepository extends JpaRepository<StockReport, Long> {

  @Query("SELECT sh FROM StockReport sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND (:productId IS NULL OR sh.product.id = :productId) AND sh.createdAt BETWEEN :startDate and :endDate ORDER BY sh.createdAt DESC")
  Page<StockReport> getAll(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate, @Param("productId") Long productId, @Param("storeId") Long storeId , Pageable pageable );

  @Query("SELECT sh.product.id as productId, SUM(sh.addition) as additions, SUM(sh.reduction) as deductions, sh.product.name as productName FROM StockReport sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND (:productId IS NULL OR sh.product.id = :productId) AND sh.createdAt BETWEEN :startDate and :endDate GROUP BY sh.product.id")
  StockSummaryDTO getSummary(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate,@Param("storeId") Long storeId, @Param("productId") Long productId );

  @Query(
    "SELECT sh.product.id as productId, SUM(sh.addition) as additions, SUM(sh.reduction) as deductions, sh.product.name as productName, sh.store.id as storeId, sh.store.name as storeName, FUNCTION('MONTH', sh.createdAt) as month FROM StockReport sh WHERE (:storeId IS NULL OR sh.store.id = :storeId) AND (:productId IS NULL OR sh.product.id = :productId) AND (sh.createdAt BETWEEN :startDate and :endDate) GROUP BY FUNCTION('MONTH', sh.createdAt), sh.store.id, sh.product.id")
  Page<StockSummaryDTO> getAllSummary(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate, @Param("productId") Long productId, @Param("storeId") Long storeId , Pageable pageable );

}
