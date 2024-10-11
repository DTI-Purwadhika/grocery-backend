package com.finpro.grocery.cart.repository;

import java.util.Optional;

import com.finpro.grocery.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.finpro.grocery.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>  {
  @Query("SELECT c FROM Cart c WHERE c.user = :user AND c.store.id = :storeId")
  Optional<Cart> findCart(User user, Long storeId);

  @Query("SELECT c FROM Cart c WHERE c.user = :user")
  Cart findByUser(User user);

  boolean existsByUserId(Long userId);
}
