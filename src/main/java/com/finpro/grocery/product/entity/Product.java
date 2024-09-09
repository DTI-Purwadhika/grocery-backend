package com.finpro.grocery.product.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.finpro.grocery.category.entity.Category;
import com.finpro.grocery.inventory.entity.Inventory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name = "products")
@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Product name is required")
  @Size(max = 100, message = "Product name must not exceed 100 characters")
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @NotBlank(message = "Product code is required")
  @Size(max = 10, message = "Product code must not exceed 10 characters")
  @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Product code must be alphanumeric")
  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "description")
  private String description;

  @NotNull(message = "Price is required")
  @Digits(integer = 12, fraction = 2, message = "Invalid price format")
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<ProductImage> images = new ArrayList<>();

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Inventory> inventory = new ArrayList<>();

  // @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)	
  // @JsonManagedReference
  // private List<Discount> discounts = new ArrayList<>();

  @NotNull(message = "Category is required")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  @JsonBackReference
  private Category category;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();
  
  @Column(name = "deleted_at")
  private Instant deletedAt;

  public void addImage(ProductImage image) {
    images.add(image);
    image.setProduct(this);
  }

  public void removeImage(ProductImage image) {
    images.remove(image);
    image.setProduct(null);
  }
  
}