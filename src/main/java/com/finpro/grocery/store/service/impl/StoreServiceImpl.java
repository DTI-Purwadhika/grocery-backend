package com.finpro.grocery.store.service.impl;

import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.store.dto.StoreDTO;
import com.finpro.grocery.store.service.StoreService;
import org.springframework.stereotype.Service;

import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.repository.StoreRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreServiceImpl implements StoreService {
  public final StoreRepository storeRepository;
 public StoreServiceImpl(StoreRepository storeRepository){
   this.storeRepository = storeRepository;
 }

  @Override
  public Iterable<Store> getAll() {
    return storeRepository.findAll();
  }

  @Override
  public Store getStoreById(Long id) {
    return storeRepository.findById(id).orElse(null);
  }

  @Override
  public Store createStore(StoreDTO storeDTO) {
     Store store = storeDTO.toEntity();
     storeRepository.save(store);

     return store;
  }

  @Transactional
  @Override
    public Store updateStore(String name, StoreDTO storeDTO) {
        Store store = storeRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        store.setName(storeDTO.getName());
        store.setAddress(storeDTO.getAddress());
        store.setPostcode(storeDTO.getPostcode());
        store.setLatitude(storeDTO.getLat());
        store.setLongitude(storeDTO.getLng());

        return storeRepository.save(store);
    }
}


