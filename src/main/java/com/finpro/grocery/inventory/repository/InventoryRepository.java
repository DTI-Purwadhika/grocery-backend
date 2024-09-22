package com.finpro.grocery.inventory.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO;
import com.finpro.grocery.inventory.entity.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  boolean existsByCode(String code);

  Optional<Inventory> findByCode(String code);

  @Query("SELECT COUNT(i) > 0 FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
  boolean isExist(@Param("productId") Long productId, @Param("storeId") Long storeId);

  @Query("SELECT new com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO(i.id, i.code, i.product.name, i.store.name, i.stock) FROM Inventory i WHERE (:productId IS NULL OR i.product.id = :productId) AND LOWER(i.store.name) LIKE CONCAT('%', LOWER(:storeName), '%')")
  Page<ResponseInventoryDTO> getStockByProduct(@Param("productId") Long productId, @Param("storeName") String storeName, Pageable pageable);
  
  @Query("SELECT new com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO(i.id, i.code, i.product.name, i.store.name, i.stock) FROM Inventory i WHERE (:storeId IS NULL OR i.store.id = :storeId) AND LOWER(i.product.name) LIKE CONCAT('%', LOWER(:productName), '%')")
  Page<ResponseInventoryDTO> getAll(@Param("storeId") Long storeId, @Param("productName") String productName, Pageable pageable);

  @Query("SELECT new com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO(i.product.id, null, i.product.name, null, SUM(i.stock)) FROM Inventory i WHERE LOWER(i.product.name) LIKE LOWER(CONCAT('%', :productName, '%')) GROUP BY i.product.id, i.product.name ORDER BY i.product.name ASC")
  Page<ResponseInventoryDTO> getList(@Param("productName") String productName, Pageable pageable);

  @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
  Optional<Inventory> checkStock(@Param("productId") Long productId, @Param("storeId") Long storeId);

  @Query("SELECT new com.finpro.grocery.inventory.dto.response.ResponseInventoryDTO(i.product.id, i.product.name, SUM(i.stock)) FROM Inventory i WHERE i.product.id = :productId GROUP BY i.product.id, i.product.name")
  Optional<ResponseInventoryDTO> checkStock(@Param("productId") Long productId);


}
