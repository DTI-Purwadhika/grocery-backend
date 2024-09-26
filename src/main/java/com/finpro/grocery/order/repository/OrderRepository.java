package com.finpro.grocery.order.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByCode(String code);

  @Query("SELECT c FROM Order c WHERE c.deletedAt IS NULL AND (LOWER(c.code) LIKE LOWER(CONCAT('%', :code, '%'))) AND (:userId IS NULL OR c.user = :userId) AND (:storeId IS NULL OR c.store.id = :storeId) AND (:start IS NULL OR c.createdAt >= :start) AND (:end IS NULL OR c.createdAt <= :end) ORDER BY c.createdAt DESC")
  Page<Order> getAll(@Param("code") String code, @Param("userId") Long userId, @Param("storeId") Long storeId, @Param("start") Instant start, @Param("end") Instant end, Pageable pageable);

}