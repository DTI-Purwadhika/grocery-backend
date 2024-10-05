package com.finpro.grocery.store.controller;

import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.store.dto.StoreRequestDTO;
import com.finpro.grocery.store.dto.StoreResponseDTO;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.StoreService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {
  private final StoreService storeService;
 public StoreController(StoreService storeService){
   this.storeService = storeService;
 }

//  @GetMapping
//  public ApiResponse<Iterable<Store>> getAll() {
//    Iterable<Store> stores = storeService.getAll();
//    return new ApiResponse<>("OK", "200", stores);
//  }

  @GetMapping("")
  public ApiResponse<Pagination<StoreResponseDTO>> getAllStores(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String city,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortDir){
     return new ApiResponse<>("OK", "200", storeService.getAllStores(name, city, page, size, sortBy, sortDir));
 }

  @PostMapping("/create")
  public ApiResponse<StoreResponseDTO> createStore(@RequestBody StoreRequestDTO storeRequestDTO){
    return new ApiResponse<>("OK", "200", storeService.createStore(storeRequestDTO));
  }

}