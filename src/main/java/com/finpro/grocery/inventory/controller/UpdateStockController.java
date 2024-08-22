package com.finpro.grocery.inventory.controller;

import com.finpro.grocery.inventory.dto.GetInventoryDTO;
import com.finpro.grocery.inventory.dto.SaveInventoryDTO;
import com.finpro.grocery.inventory.service.UpdateStock;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
public class UpdateStockController {
  
  @Autowired
  private UpdateStock inventoryService;

  @PutMapping("/{id}")
  public ApiResponse<GetInventoryDTO> updateInventory(@PathVariable Long id, @RequestBody SaveInventoryDTO inventory) {
    GetInventoryDTO updatedInventory = inventoryService.updateInventory(id, inventory);
    return new ApiResponse<>("OK", "200", updatedInventory);
  }
  
}
