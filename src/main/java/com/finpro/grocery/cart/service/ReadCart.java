package com.finpro.grocery.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.cart.dto.response.GetCartResponse;
import com.finpro.grocery.cart.entity.Cart;
import com.finpro.grocery.cart.repository.CartRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;

@Service
public class ReadCart {

  @Autowired
  private CartRepository cartRepository;

  Cart getCart(Long cartId) {
    return cartRepository.findById(cartId)
      .orElseThrow(() -> new ResourceNotFoundException("Cart with id: " + cartId + " not found"));
  }

  public Pagination<GetCartResponse> getCart(Long userId, int page, int size, String sortBy, String sortDir) {
    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);
    
    Page<Cart> carts =  cartRepository.findByUserId(userId, pageable);
    
    Page<GetCartResponse> cartResponse = carts.map(this::convertToDto);
    System.out.println(cartResponse.getTotalElements());
    System.out.println(cartResponse.getContent());
    
    return new Pagination<>(
      cartResponse.getTotalPages(),
      cartResponse.getTotalElements(),
      cartResponse.isFirst(),
      cartResponse.isLast(),
      cartResponse.getContent()
    );
  }

  private GetCartResponse convertToDto(Cart cart) {
    GetCartResponse response = CartDTOConverter.convertToDto(cart);
    return response;
  }

}
