package com.finpro.grocery.cart.service;

import java.util.Optional;

import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.dto.request.AddToCartRequest;
import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.entity.CartItem;
import com.finpro.grocery.cart.repository.CartItemRepository;
import com.finpro.grocery.cart.repository.CartRepository;
import com.finpro.grocery.inventory.service.ReadStock;
import com.finpro.grocery.product.entity.Product;
import com.finpro.grocery.product.service.ReadProduct;
import com.finpro.grocery.share.exception.BadRequestException;

@Service
public class CreateCart {
  
  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  @Autowired
  private ReadStock stockService;

  @Autowired
  private ReadProduct productService;

  @Autowired
  private UserRepository userRepository;

  public GetCartResponse addToCart(String userId, AddToCartRequest request) {
    User user = userRepository.findByEmail(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    Product product = productService.getProductById(request.getProductId());
    
    if (request.getQuantity() <= 0) 
      throw new BadRequestException("Quantity must be greater than 0");
   
    stockService.checkStockProduct(request.getProductId(), request.getQuantity());
   
    Cart cart = cartRepository.findByUserId(user.getId())
      .orElse(new Cart(user));

    cartRepository.save(cart);
    
    Optional<CartItem> existingCartItem = cart.getItems().stream()
      .filter(item -> item.getProduct().getId().equals(request.getProductId()))
      .findFirst();

    if (existingCartItem.isPresent()) {
      CartItem cartItem = existingCartItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());

      if (stockService.checkStockProduct(request.getProductId(), cartItem.getQuantity())) 
        throw new BadRequestException("Insufficient stock available for the updated quantity");

      cartItemRepository.save(cartItem);

    } else {
      CartItem cartItem = new CartItem();
      cartItem.setCart(cart);
      cartItem.setProduct(product);
      cartItem.setQuantity(request.getQuantity());
      cart.addItem(cartItem);
      cartItemRepository.save(cartItem);
    }

    cartRepository.save(cart);
    return CartDTOConverter.convertToDto(cart);
  }
  
}
