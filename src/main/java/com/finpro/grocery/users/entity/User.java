package com.finpro.grocery.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.finpro.grocery.store.entity.Store;

import java.time.Instant;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Size(max = 150)
    @NotNull
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    private Boolean isVerified = false;

    @Size(max = 100)
    @Column(name = "password", length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role;

    @Size(max = 250)
    @Column(name = "profile_picture", length = 250)
    private String profilePicture;

    @Size(max = 20)
    @Column(name = "referral_code", unique = true, length = 20)
    private String referralCode;

    @JoinColumn(name = "store_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JsonBackReference
    private Store store;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private Instant deletedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = Instant.now();
    }

    public enum UserRole {
        CUSTOMER,
        ADMIN,
        SUPER
    }
}