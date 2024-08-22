package com.finpro.grocery.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.repository.InventoryRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import java.time.Instant;

@Service
public class DeleteStock {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ReadStock read;

  @Transactional
  public Inventory removeInventory(Long id) {
    Inventory inventory = read.getInventory(id);

    if(inventory.getDeletedAt() != null) 
      throw new ResourceNotFoundException("Inventory with name " + inventory.getProduct().getName() + " for store " + inventory.getStore().getName() +  " already deleted");

    inventory.setUpdatedAt(Instant.now());
    inventory.setDeletedAt(Instant.now());

    return inventoryRepository.save(inventory);
  }

  @Transactional
  public Inventory restoreInventory(Long id) {
    Inventory inventory = read.getInventory(id);

    if(inventory.getDeletedAt() == null) 
    throw new ResourceNotFoundException("Inventory with name " + inventory.getProduct().getName() + " for store " + inventory.getStore().getName() +  " not yet deleted");

    inventory.setUpdatedAt(Instant.now());
    inventory.setDeletedAt(null);

    return inventoryRepository.save(inventory);
  }
  
}
