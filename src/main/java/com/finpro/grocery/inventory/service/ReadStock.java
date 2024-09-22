package com.finpro.grocery.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.repository.InventoryRepository;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;

@Service
public class ReadStock {
  @Autowired
  private InventoryRepository inventoryRepository;

  @Autowired
  private ReadProduct productService;

  public Pagination<ResponseInventoryDTO> getAll(String keywordProduct, Long storeId, Long productId, int page, int size, String sortBy, String sortDir, boolean isGroup) {
    String productName = keywordProduct == null ? "" : keywordProduct;

    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<ResponseInventoryDTO> inventories;

   
    if(productId != null && productService.getProductById(productId) == null) 
      throw new ResourceNotFoundException("Product not found");
      inventories = inventoryRepository.getAll(storeId, productName, pageable);

    return new Pagination<>(
      inventories.getTotalPages(),
      inventories.getTotalElements(),
      inventories.isFirst(),
      inventories.isLast(),
      inventories.getContent()
    );
  }

  public Inventory getInventory(Long id) {
    return inventoryRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Inventory with id: " + id));
  }

  public ResponseInventoryDTO getInventoryDetail(Long id) {
    Inventory inventory = getInventory(id);
    ResponseInventoryDTO response = DTOConverter.convertToDto(inventory);
    return response;
  }

  public Inventory checkStock(Long productId, Long storeId) {
    return inventoryRepository.checkStock(productId, storeId)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Inventory with product id: " + productId + " and store id: " + storeId));
  }

  public Inventory checkStock(Long productId, Long storeId, int quantity) {
    Inventory inventory = checkStock(productId, storeId);
    if(inventory.getStock() < quantity) 
      throw new ResourceNotFoundException("There's no enough stock for product id: " + productId + " and store id: " + storeId);
    return inventory;
  }

  public ResponseInventoryDTO checkStockProduct(Long productId) {
    return inventoryRepository.checkStockByProduct(productId)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Inventory with product id: " + productId ));
  }

  public boolean checkStockProduct(Long productId, int quantity) {
    ResponseInventoryDTO inventory = checkStockProduct(productId);
    return inventory.getTotalStock() < quantity ? false : true;
  }
}
