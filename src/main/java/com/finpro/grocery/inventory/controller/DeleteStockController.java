package com.finpro.grocery.inventory.controller;

import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.service.DeleteStock;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
public class DeleteStockController {

  @Autowired
  private DeleteStock inventoryService;

  @DeleteMapping("/{id}")
  public ApiResponse<ResponseInventoryDTO> removeInventory(@PathVariable Long id) {
    ResponseInventoryDTO deletedStock = inventoryService.removeInventory(id);
    return new ApiResponse<>("DELETED", "200", deletedStock);
  }
  
}
