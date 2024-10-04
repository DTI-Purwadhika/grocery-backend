package com.finpro.grocery.store.service;

import com.finpro.grocery.store.dto.StoreDTO;
import com.finpro.grocery.store.entity.Store;

public interface StoreService {
    public Iterable<Store> getAll();
    public Store getStoreById(Long id);
    public Store createStore(StoreDTO storeDTO);
    public Store updateStore(String name, StoreDTO storeDTO);
}
