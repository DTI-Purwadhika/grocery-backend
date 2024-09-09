package com.finpro.grocery.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.repository.InventoryRepository;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.sequence.service.SequenceService;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.StoreService;

import jakarta.transaction.Transactional;

@Service
public class CreateStock {
  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private StoreService storeService;

  @Autowired
  private SequenceService sequenceService;

  @Transactional
  public ResponseInventoryDTO saveInventory(RequestInventoryDTO inventoryDTO) {
    if (inventoryRepository.isExist(inventoryDTO.getProductId(), inventoryDTO.getStoreId()))
      throw new BadRequestException("Inventory already exists for this product and store");	

    Product product = productService.getProductById(inventoryDTO.getProductId());
    if (product == null) throw new ResourceNotFoundException("Product not found");
    
    Store store = storeService.getStoreById(inventoryDTO.getStoreId());
    if (store == null) throw new ResourceNotFoundException("Store not found");

    Inventory inventory = new Inventory();
    inventory.setProduct(product);
    inventory.setStore(store);
    inventory.setStock(inventoryDTO.getStock());
    inventory.setCode(sequenceService.generateUniqueCode("inventory_code_sequence", "IVT%05d"));	
    inventoryRepository.save(inventory);

    ResponseInventoryDTO createdInventory = new ResponseInventoryDTO();
    createdInventory.setId(inventory.getId());
    createdInventory.setCode(inventory.getCode());
    createdInventory.setName(product.getName());
    createdInventory.setStoreName(store.getName());
    createdInventory.setTotalStock(inventory.getStock());

    return createdInventory;
  }
}
