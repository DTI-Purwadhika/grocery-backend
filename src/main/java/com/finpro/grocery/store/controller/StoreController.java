package com.finpro.grocery.store.controller;

import com.cloudinary.Api;
import com.finpro.grocery.auth.helper.Claims;
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

  @GetMapping("/{id}")
  public ApiResponse<StoreResponseDTO> getStoreById(@PathVariable("id") Long id){
      return new ApiResponse<>("OK", "200", storeService.getStoreDTOById(id));
  }

  @GetMapping("")
  public ApiResponse<Pagination<StoreResponseDTO>> getAllStores(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false) String city,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortDir){
     return new ApiResponse<>("OK", "200", storeService.getAllStores(keyword, city, page, size, sortBy, sortDir));
 }

  @PostMapping("/create")
  public ApiResponse<StoreResponseDTO> createStore(@RequestBody StoreRequestDTO storeRequestDTO){
    return new ApiResponse<>("OK", "200", storeService.createStore(storeRequestDTO));
  }

  @PutMapping("/{id}")
  public ApiResponse<StoreResponseDTO> updateStore(@PathVariable("id") Long id, @RequestBody StoreRequestDTO storeRequestDTO){
     return new ApiResponse<>("OK", "200", storeService.updateStore(id, storeRequestDTO));
  }

  @DeleteMapping("/{id}")
  public ApiResponse<?> deleteStore(@PathVariable("id") Long id){
      storeService.deleteStore(id);
      return new ApiResponse<>("OK", "200", "Store deleted successfully");
  }

  @GetMapping("/nearest")
  public ApiResponse<Store> getNearestStore(){
      var claims = Claims.getClaimsFromJwt();
      String currentUserEmail = (String) claims.get("sub");

      return new ApiResponse<>("OK", "200", storeService.getNearestStore(currentUserEmail));
  }

}