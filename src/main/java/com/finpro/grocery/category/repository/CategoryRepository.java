package com.finpro.grocery.category.repository;

import com.finpro.grocery.category.entity.Category;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE lower(c.name) = lower(:name) AND c.deletedAt IS NULL")
  boolean isExist(@Param("name") String name);
  
  @Query("SELECT c FROM Category c WHERE c.deletedAt IS NULL AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))")
  Page<Category> getAll(@Param("name") String name, Pageable pageable);

  @Query("SELECT c.name FROM Category c WHERE c.deletedAt IS NULL AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))")
  Page<Category> getCategoriesName(@Param("name") String name, Pageable pageable);

  @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name)")
  Optional<Category> getByName(@Param("name") String name);
  
}