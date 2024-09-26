package com.finpro.grocery.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finpro.grocery.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
  Iterable<ProductImage> findByProductId(Long productId);

  @Query("SELECT pi FROM ProductImage pi WHERE pi.id = ?1 AND pi.deletedAt IS NULL")
  Optional<ProductImage> getProductImage(Long id);

  @Query("SELECT pi FROM ProductImage pi WHERE pi.deletedAt IS NULL")
  Iterable<ProductImage> getAll(Long productId);
}
