package com.finpro.grocery.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.store.entity.Store;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    @Query(value = "SELECT s.* FROM stores s ORDER BY (6371 * ACOS(\n" +
        "    COS(RADIANS(:latitude)) * COS(RADIANS(s.latitude)) * COS(RADIANS(s.longitude) - RADIANS(:longitude))\n" +
        "    + SIN(RADIANS(:latitude)) * SIN(RADIANS(s.latitude))\n" +
        "  )) LIMIT 1", nativeQuery = true)
    Store getNearestStore(@Param("longitude") float longitude, @Param("latitude") float latitude);
} 