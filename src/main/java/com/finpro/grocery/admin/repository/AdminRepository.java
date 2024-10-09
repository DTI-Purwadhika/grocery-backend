package com.finpro.grocery.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finpro.grocery.users.entity.User;

public interface AdminRepository extends JpaRepository<User, Long> {

    @Query("SELECT c FROM User c WHERE c.deletedAt IS NULL AND (LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) OR (LOWER(c.email) LIKE LOWER(CONCAT('%', :name, '%')))) AND (:role IS NULL OR c.role = :role)")
    Page<User> getAll(@Param("name") String name, @Param("role") User.UserRole role, Pageable pageable);
  
} 
