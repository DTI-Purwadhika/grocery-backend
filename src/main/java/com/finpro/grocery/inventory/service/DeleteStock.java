package com.finpro.grocery.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.repository.InventoryRepository;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class DeleteStock {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ReadStock read;

  @Transactional
  public ResponseInventoryDTO removeInventory(Long id) {
    Inventory inventory = read.getInventory(id);

    inventory.setUpdatedAt(Instant.now());
    inventory.setStock(0L);
    inventoryRepository.save(inventory);

    return convertToDto(inventory);
  }

  private ResponseInventoryDTO convertToDto(Inventory inventory) {
    ResponseInventoryDTO responseInventoryDTO = new ResponseInventoryDTO();
    responseInventoryDTO.setId(inventory.getId());
    responseInventoryDTO.setCode(inventory.getCode());
    responseInventoryDTO.setTotalStock(inventory.getStock());
    responseInventoryDTO.setStoreName(inventory.getStore().getName());
    responseInventoryDTO.setName(inventory.getProduct().getName());

    return responseInventoryDTO;
  }
}
