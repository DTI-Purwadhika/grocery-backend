package com.finpro.grocery.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.repository.InventoryRepository;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.store.service.StoreService;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class UpdateStock {

  @Autowired
  private InventoryRepository inventoryInventory;

  @Autowired
  private ReadStock read;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private StoreService storeService;

  @Transactional
  public ResponseInventoryDTO updateInventory(Long id, RequestInventoryDTO inventory) {
    Inventory updatedInventory = read.getInventory(id);

    if(inventory.getProductId() != null)
      updatedInventory.setProduct(productService.getProductById(inventory.getProductId()));
    
    if(inventory.getStoreId() != null)
      updatedInventory.setStore(storeService.getStoreById(inventory.getStoreId()));
    
    if(inventory.getStock() != null)
      updatedInventory.setStock(inventory.getStock());

    updatedInventory.setUpdatedAt(Instant.now());
    inventoryInventory.save(updatedInventory);
    ResponseInventoryDTO inventoryDto = DTOConverter.convertToDto(updatedInventory);

    return inventoryDto;
  }

}
