package com.finpro.grocery.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.repository.StoreRepository;

@Service
public class StoreService {
  @Autowired
  private StoreRepository storeRepository;

  public Iterable<Store> getAll() {
    return storeRepository.findAll();
  }

  public Store getStoreById(Long id) {
    return storeRepository.findById(id).orElse(null);
  }
}
