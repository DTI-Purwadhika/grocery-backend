package com.finpro.grocery.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.store.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

} 