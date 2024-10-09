package com.finpro.grocery.store.entity;

import java.time.Instant;

import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.users.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stores")
@Entity
public class Store {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Store name is required")
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "address", nullable = false)
  private String address;

  @JoinColumn(name = "city_id", nullable = true)
  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  private City city;

  @Column(name = "postcode", nullable = false)
  private String postcode;

  @Column(name = "latitude", nullable = false)
  private Float latitude;

  @Column(name = "longitude", nullable = false)
  private Float longitude;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();
  
  @Column(name = "deleted_at")
  private Instant deletedAt;
}
