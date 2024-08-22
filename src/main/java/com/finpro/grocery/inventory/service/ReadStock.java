package com.finpro.grocery.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.inventory.dto.GetInventoryDTO;
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

  public Pagination<GetInventoryDTO> getAll(String keywordProduct, String keywordStore, Long productId, int page, int size, String sortBy, String sortDir, boolean isGroup) {
    String productName = keywordProduct == null ? "" : keywordProduct;
    String storeName = keywordStore == null ? "" : keywordStore;

    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<GetInventoryDTO> inventories;

    if(isGroup) 
      inventories = inventoryRepository.getAll(productName, pageable);
    else {
      if(productId != null && productService.getProductById(productId) == null) 
        throw new ResourceNotFoundException("Product not found");
      inventories = inventoryRepository.getStockByProduct(productId, storeName, pageable);
    }

    return new Pagination<>(
      inventories.getTotalPages(),
      inventories.getTotalElements(),
      inventories.isFirst(),
      inventories.isLast(),
      inventories.getContent()
    );
  }

  public Inventory getInventory(Long id) {
    return inventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("There's no Inventory with id: " + id));
  }

}
