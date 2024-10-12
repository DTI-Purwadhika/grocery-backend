package com.finpro.grocery.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>  {

  @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
  Optional<Cart> findByUserId(Long userId);

  boolean existsByUserId(Long userId);
}
