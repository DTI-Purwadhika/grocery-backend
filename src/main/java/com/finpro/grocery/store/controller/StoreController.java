package com.finpro.grocery.store.controller;

import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.StoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

  @Autowired
  private StoreService storeService;

  @GetMapping
  public ApiResponse<Iterable<Store>> getAll() {
    Iterable<Store> stores = storeService.getAll();
    return new ApiResponse<>("OK", "200", stores);
  }

}