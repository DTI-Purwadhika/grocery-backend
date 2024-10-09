package com.finpro.grocery.reportstock.entity;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_sales")
@Entity
public class SalesReport {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  
  @NotBlank
  @Column(name = "store_id")
  private Long storeId;
  
  @NotBlank
  @Column(name = "product_id")
  private Long productId;
  
  @NotBlank
  @Column(name = "category_id")
  private Long categoryId;
  
  @NotBlank
  @Column(name = "product_name")
  private String productName;

  @Column(name = "created_at")
  private Instant createdAt;
  
  @Column(name = "updated_at")
  private Instant updatedAt;
}
