package com.finpro.grocery.shipping.entity;

import com.finpro.grocery.city.entity.City;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "origin", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private City origin;

    @JoinColumn(name = "destination", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private City destination;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "cost", nullable = false)
    private int cost;

    @Column(name = "courier", nullable = false)
    private String courier;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
