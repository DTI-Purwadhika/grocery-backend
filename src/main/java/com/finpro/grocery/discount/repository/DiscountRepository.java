package com.finpro.grocery.discount.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.discount.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
  Discount findByCode(String code);

  @Query("SELECT d FROM Discount d WHERE d.deletedAt IS NULL")
  Page<Discount> getAll(String name, Pageable pageable);
}
