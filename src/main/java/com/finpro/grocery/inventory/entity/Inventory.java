package com.finpro.grocery.inventory.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventories")
@Entity
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Inventory Code is required")
  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Min(value = 0, message = "Stock must be greater than or equal to 0")
  @Column(name = "stock", nullable = false)
  private Long stock;

  @NotNull(message = "Product is required")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  private Product product;

  @NotNull(message = "Store is required")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", nullable = false)
  @JsonBackReference
  private Store store;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();
  
  @Column(name = "deleted_at")
  private Instant deletedAt;
}