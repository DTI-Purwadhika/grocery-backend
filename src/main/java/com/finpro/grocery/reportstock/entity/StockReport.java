package com.finpro.grocery.reportstock.entity;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.store.entity.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_stock")
@Entity
public class StockReport {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id")
  @JsonBackReference
  private Product product;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "store_id")
  @JsonBackReference
  private Store store;
  
  @Column(name = "addition", nullable = false)
  private Integer addition = 0;

  @Column(name = "reduction", nullable = false)
  private Integer reduction = 0;

  @Column(name = "description")
  private String description;
  
  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();
  
}
