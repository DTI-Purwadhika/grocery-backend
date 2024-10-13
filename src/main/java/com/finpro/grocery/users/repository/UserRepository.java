package com.finpro.grocery.users.repository;

import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND (LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%')) OR (LOWER(u.email) LIKE LOWER(CONCAT('%', :name, '%')))) AND (:role IS NULL OR u.role = :role)")
    Page<User> getAll(@Param("name") String name, @Param("role") User.UserRole role, Pageable pageable);
}
