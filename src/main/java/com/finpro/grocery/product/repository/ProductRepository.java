package com.finpro.grocery.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.product.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
  boolean existsByCode(String code);

  @EntityGraph(attributePaths = "images")
  Optional<Product> findByCode(String code);
  
  @EntityGraph(attributePaths = "images")
  @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
  List<Product> getAll();

  @EntityGraph(attributePaths = "images")
  @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL AND LOWER(p.name) LIKE CONCAT('%',LOWER(:name),'%') AND LOWER(p.code) LIKE CONCAT('%', LOWER(:code), '%') AND (:category IS NULL OR p.category.id = :category)")
  Page<Product> getAll(@Param("name") String name, @Param("code") String code, @Param("category") Long category, Pageable pageable );
}
