package com.finpro.grocery.inventory.controller;

import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.service.CreateStock;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
public class PostStockController {

  @Autowired
  private CreateStock stockService;

  @PostMapping
  public ApiResponse<ResponseInventoryDTO> saveInventory(@RequestBody RequestInventoryDTO inventory) {
    return new ApiResponse<>("OK", "200", stockService.saveInventory(inventory));
  }

  @PostMapping("/generate-stock")
  public ApiResponse<String> generateStock() {
    stockService.generateStock();
    return new ApiResponse<>("OK", "200", "Done");
  }
  
}
