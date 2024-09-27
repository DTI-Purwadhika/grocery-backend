package com.finpro.grocery.inventory.service;

import com.finpro.grocery.inventory.dto.request.RequestInventoryDTO;
import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.share.sequence.service.SequenceService;
import com.finpro.grocery.store.entity.Store;

class DTOConverter {

  static ResponseInventoryDTO convertToDto(Inventory inventory) {
    ResponseInventoryDTO response = new ResponseInventoryDTO();
    response.setId(inventory.getId());
    response.setName(inventory.getProduct().getName());
    response.setStoreName(inventory.getStore().getName());
    response.setTotalStock(inventory.getStock());
    response.setCode(inventory.getCode());
    return response;
  }

  static Inventory convertToEntity(RequestInventoryDTO inventory, Product product, Store store, SequenceService sequenceService) {
    Inventory request = new Inventory();
    
    request.setProduct(product);
    request.setStore(store);
    request.setStock(inventory.getStock());
    request.setCode(sequenceService.generateUniqueCode("inventory_code_sequence", "IVT%05d"));

    return request;
  }

}
