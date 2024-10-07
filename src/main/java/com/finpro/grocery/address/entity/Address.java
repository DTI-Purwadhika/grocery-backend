package com.finpro.grocery.address.entity;

import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.users.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_name", nullable = false)
    private String addressName;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Column(name = "latitude", nullable = false)
    private Float latitude;

    @Column(name = "longitude", nullable = false)
    private Float longitude;

    @JoinColumn(name = "user_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private User user;

    @JoinColumn(name = "city_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private City city;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
