package com.finpro.grocery.store.service.impl;

import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.city.service.CityService;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.store.dto.StoreRequestDTO;
import com.finpro.grocery.store.dto.StoreResponseDTO;
import com.finpro.grocery.store.service.StoreService;
import com.finpro.grocery.store.specification.StoreSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.repository.StoreRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreServiceImpl implements StoreService {
  private final StoreRepository storeRepository;
  private final CityService cityService;
 public StoreServiceImpl(StoreRepository storeRepository, CityService cityService){
   this.storeRepository = storeRepository;
   this.cityService = cityService;
 }

  @Override
  public Store getStoreById(Long id) {
    return storeRepository.findById(id).orElse(null);
  }

    @Override
    public Pagination<StoreResponseDTO> getAllStores(String name, String city, int page, int size, String sortBy, String sortDir) {
        Specification<Store> storeSpecification = Specification.where(StoreSpecification.byName(name)
                .and(StoreSpecification.byCity(city)));

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Store> stores = storeRepository.findAll(storeSpecification, pageable);

        Page<StoreResponseDTO> storesDTO = stores.map(StoreResponseDTO::toDto);

        return new Pagination<>(
            storesDTO.getTotalPages(),
            storesDTO.getTotalElements(),
            storesDTO.isFirst(),
            storesDTO.isLast(),
            storesDTO.getContent()
        );
    }

    @Override
  public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO) {
     Store store = new Store();
     City city = cityService.getCity(storeRequestDTO.getCityId());
     store.setName(storeRequestDTO.getName());
     store.setAddress(storeRequestDTO.getAddress());
     store.setCity(city);
     store.setPostcode(storeRequestDTO.getPostcode());
     store.setLatitude(storeRequestDTO.getLat());
     store.setLongitude(storeRequestDTO.getLng());
     storeRepository.save(store);

     return StoreResponseDTO.toDto(store);
  }

  @Transactional
  @Override
    public StoreResponseDTO updateStore(Long id, StoreRequestDTO storeRequestDTO) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Store not found"));
        City city = cityService.getCity(storeRequestDTO.getCityId());
        store.setName(storeRequestDTO.getName());
        store.setAddress(storeRequestDTO.getAddress());
        store.setCity(city);
        store.setPostcode(storeRequestDTO.getPostcode());
        store.setLatitude(storeRequestDTO.getLat());
        store.setLongitude(storeRequestDTO.getLng());
        storeRepository.save(store);

        return StoreResponseDTO.toDto(store);
    }
}


