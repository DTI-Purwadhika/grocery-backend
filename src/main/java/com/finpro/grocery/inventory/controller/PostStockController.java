package com.finpro.grocery.inventory.controller;

import com.finpro.grocery.inventory.dto.GetInventoryDTO;
import com.finpro.grocery.inventory.dto.SaveInventoryDTO;
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
  public ApiResponse<GetInventoryDTO> saveInventory(@RequestBody SaveInventoryDTO inventory) {
    return new ApiResponse<>("OK", "200", stockService.saveInventory(inventory));
  }

}
