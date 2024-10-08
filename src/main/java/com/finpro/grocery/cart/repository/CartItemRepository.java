package com.finpro.grocery.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.cart.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>  {
  
}
