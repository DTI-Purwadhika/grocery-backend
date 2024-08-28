package com.finpro.grocery.inventory.controller;

import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.service.ReadStock;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
public class GetStockController {

  @Autowired
  private ReadStock inventoryService;

  @GetMapping
  public ApiResponse<Pagination<ResponseInventoryDTO>> getAll(
    @RequestParam(required = false) String productName,
    @RequestParam(required = false) String storeName,
    @RequestParam(required = false) Long productId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "product.name") String sortBy,
    @RequestParam(defaultValue = "asc") String sortDir,
    @RequestParam(defaultValue = "true") boolean isGroup
  ) {
    System.out.println("GetStockController.getAll");
    return new ApiResponse<>("OK", "200", inventoryService.getAll(productName, storeName, productId, page, size, sortBy, sortDir, isGroup));
  }

  @GetMapping("/{id}")
  public ApiResponse<ResponseInventoryDTO> getInventoryDetail(@PathVariable Long id) {
    ResponseInventoryDTO inventory = inventoryService.getInventoryDetail(id);
    return new ApiResponse<>("OK", "200", inventory);
  }

}
