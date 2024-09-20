package com.finpro.grocery.cart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.dto.request.AddToCartRequest;
import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.cart.repository.CartItemRepository;
import com.finpro.grocery.cart.repository.CartRepository;
import com.finpro.grocery.inventory.entity.Inventory;
import com.finpro.grocery.inventory.service.ReadStock;
import com.finpro.grocery.share.exception.BadRequestException;

@Service
public class CreateCart {
  
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private ReadStock stockService;

  // ! Still use user dummy data for now, change when user is implemented
  // @Autowired
  // private UserRepository userRepository;

  public GetCartResponse addToCart(Long userId, AddToCartRequest request) {
    // ! Check if the user is registered and verified, change when user is implemented
    // User user = userRepository.findById(userId)
    //     .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    
    // if (!user.isVerified()) 
    //   throw new AccessDeniedException("User not verified");

    if (request.getQuantity() <= 0) 
      throw new BadRequestException("Quantity must be greater than 0");
   
    Inventory stock = stockService.checkStock(request.getProductId(), request.getStoreId());

    if (stock.getStock() < request.getQuantity())
      throw new BadRequestException("Insufficient stock available");
   
    // ! Still use user dummy data for now, change when user is implemented
    Cart cart = cartRepository.findCart(1L, request.getStoreId())
      .orElse(new Cart(1L, stock.getStore()));

    cartRepository.save(cart);
    
    Optional<CartItem> existingCartItem = cart.getItems().stream()
      .filter(item -> item.getProduct().getId().equals(request.getProductId()))
      .findFirst();

    if (existingCartItem.isPresent()) {
      CartItem cartItem = existingCartItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

      if (cartItem.getQuantity() > stock.getStock()) 
        throw new BadRequestException("Insufficient stock available for the updated quantity");

      cartItemRepository.save(cartItem);

    } else {
      CartItem cartItem = new CartItem();
      cartItem.setCart(cart);
      cartItem.setProduct(stock.getProduct());
      cartItem.setQuantity(request.getQuantity());
      cart.addItem(cartItem);
      cartItemRepository.save(cartItem);
    }

    cartRepository.save(cart);
    return CartDTOConverter.convertToDto(cart);
  }
  
}
