package com.finpro.grocery.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.users.entity.User;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
  private User user;

  // @ManyToOne(fetch = FetchType.LAZY, optional = true)
  // @JoinColumn(name = "discount_id", nullable = true)
  // @JsonBackReference
  // private Discount discount;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "store_id", nullable = true)
  @JsonBackReference
  private Store store;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;
  
  @NotBlank(message = "Order Code is required")
  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @NotBlank(message = "Invoice URL is required")
  @Column(name = "invoice_url", nullable = false)
  private String invoiceUrl;

  @Column(name = "proof_url", nullable = true)
  private String proofUrl;

  @Column(name = "resi_number", nullable = true)
  private String resiNumber;

  @Column(name = "description", nullable = true)
  private String description;

  @NotNull(message = "Total Amount is required")
  @Column(name = "total_amount", nullable = false)
  @Min(value = 0, message = "Total amount must be greater than 0")
  private BigDecimal totalAmount = BigDecimal.ZERO;

  @NotNull(message = "Total Shipment is required")
  @Column(name = "total_shipment", nullable = false)
  @Min(value = 0, message = "Total shipment must be greater than 0")
  private BigDecimal totalShipment = BigDecimal.ZERO;

  @NotNull(message = "Total Discount is required")
  @Column(name = "total_discount", nullable = true)
  @Min(value = 0, message = "Total discount must be greater than 0")
  private BigDecimal totalDiscount = BigDecimal.ZERO;

  @NotNull(message = "Total Payment is required")
  @Column(name = "total_payment", nullable = false)
  @Min(value = 0, message = "Total payment must be greater than 0")
  private BigDecimal totalPayment = BigDecimal.ZERO;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<OrderProduct> items = new ArrayList<>();

  @NotNull(message = "Expiry Date is required")
  @Column(name = "expiry_date", nullable = false)
  private String expiryDate;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();

}
