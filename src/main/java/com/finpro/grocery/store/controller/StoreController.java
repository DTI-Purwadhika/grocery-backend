package com.finpro.grocery.store.controller;

import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.store.dto.StoreDTO;
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

  @GetMapping
  public ApiResponse<Iterable<Store>> getAll() {
    Iterable<Store> stores = storeService.getAll();
    return new ApiResponse<>("OK", "200", stores);
  }

  @PostMapping("/create")
  public ApiResponse<Store> createStore(@RequestBody StoreDTO storeDTO){
    return new ApiResponse<>("OK", "200", storeService.createStore(storeDTO));
  }

}