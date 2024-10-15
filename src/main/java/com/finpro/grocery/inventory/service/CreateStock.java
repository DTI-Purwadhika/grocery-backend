package com.finpro.grocery.inventory.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.repository.InventoryRepository;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.repository.ProductRepository;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.share.exception.BadRequestException;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.sequence.service.SequenceService;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.repository.StoreRepository;
import com.finpro.grocery.store.service.impl.StoreServiceImpl;

import jakarta.transaction.Transactional;

@Service
public class CreateStock {
  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StoreRepository storeRepository;

  @Autowired
  private StoreServiceImpl storeServiceImpl;

  @Autowired
  private SequenceService sequenceService;

  @Transactional
  public ResponseInventoryDTO saveInventory(RequestInventoryDTO inventoryDTO) {
    if (inventoryRepository.isExist(inventoryDTO.getProductId(), inventoryDTO.getStoreId()))
      throw new BadRequestException("Inventory already exists for this product and store");	

    Product product = productService.getProductById(inventoryDTO.getProductId());
    if (product == null) throw new ResourceNotFoundException("Product not found");
    
    Store store = storeServiceImpl.getStoreById(inventoryDTO.getStoreId());
    if (store == null) throw new ResourceNotFoundException("Store not found");

    Inventory inventory = DTOConverter.convertToEntity(inventoryDTO, product, store, sequenceService);
    inventoryRepository.save(inventory);

    return DTOConverter.convertToDto(inventory);
  }

  @Transactional
  public void generateStock() {
    Random rand = new Random();

    List<Product> products = productRepository.findAll();
    List<Store> stores = storeRepository.findAll();
    for (Product product : products) {
      for (Store store : stores) {
        if (!inventoryRepository.isExist(product.getId(), store.getId())) {
          Inventory inventory = new Inventory();
          inventory.setCode(sequenceService.generateUniqueCode("inventory_code_sequence", "IVT%05d"));
          inventory.setProduct(product);
          inventory.setStore(store);
          long stock = rand.nextLong(101); 
          if (stock < 0) stock = -stock;
          inventory.setStock(stock); 

          inventoryRepository.save(inventory);
        }
      }
    }
  }

}
