package com.finpro.grocery.discount.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.store.entity.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "discounts")
@Entity
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Discount Name is required")
  @Column(name = "name", nullable = false, unique = false)
  private String name;

  @NotBlank(message = "Inventory Code is required")
  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "description")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DiscountType type;

  @NotNull(message = "Discount Value is required")
  @Column(name = "value", nullable = false)
  private double value;

  @Column(name = "minPurchaseAmount")
  private double minPurchaseAmount;

  @Column(name = "maxDiscountAmount")
  private Double maxDiscountAmount;

  // @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  // @JoinColumn(name = "product_id")
  // @JsonBackReference
  // private Product product;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "store_id")
  @JsonBackReference
  private Store store;

  @Column(name = "start_date", nullable = false)
  private Instant startDate = Instant.now();

  @Column(name = "end_date")
  private Instant endDate;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();
  
  @Column(name = "deleted_at")
  private Instant deletedAt;

}
