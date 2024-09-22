package com.finpro.grocery.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.inventory.entity.Inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_products")
@Entity
public class OrderProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  @JsonBackReference
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inventory_id", nullable = false)
  @JsonBackReference
  private Inventory inventory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id", nullable = false)
  @JsonBackReference
  private Discount discount;

  @Column(name = "quantity", nullable = false)
  private int quantity;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  @Column(name = "discount_amount", nullable = true)
  private BigDecimal discountAmount;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = true)
  private Instant updatedAt = Instant.now();

  @Column(name = "deleted_at", nullable = true)
  private Instant deletedAt;
}
