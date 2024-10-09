package com.finpro.grocery.store.service;

import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.store.dto.StoreRequestDTO;
import com.finpro.grocery.store.dto.StoreResponseDTO;
import com.finpro.grocery.store.entity.Store;

public interface StoreService {
    public Store getStoreById(Long id);
    public StoreResponseDTO getStoreDTOById(Long id);
    public Pagination<StoreResponseDTO> getAllStores(String name, String city, int page, int size, String sortBy, String sortDir);
    public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO);
    public StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO);
    public void deleteStore(Long id);
}
