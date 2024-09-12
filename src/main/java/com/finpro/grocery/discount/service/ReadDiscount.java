package com.finpro.grocery.discount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finpro.grocery.discount.dto.response.ResponseDiscountDTO;
import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.discount.repository.DiscountRepository;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;

@Service
public class ReadDiscount {

  @Autowired
  private DiscountRepository discountRepository;

  public Pagination<ResponseDiscountDTO> getAll(String keyword, int page, int size, String sortBy, String sortDir) {
    String name = keyword == null ? "" : keyword;

    Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Discount> discounts;
    discounts =  discountRepository.getAll(name, pageable);

    Page<ResponseDiscountDTO> discountDto = discounts.map(this::convertToDto);
    
    return new Pagination<>(
      discountDto.getTotalPages(),
      discountDto.getTotalElements(),
      discountDto.isFirst(),
      discountDto.isLast(),
      discountDto.getContent()
    );
  }

  public Discount getDiscount(Long id) {
    return discountRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("There's no Discount with id: " + id));
  }

  public ResponseDiscountDTO getDiscountDetail(Long id) {
    return DTOConverter.convertToDto(getDiscount(id));
  }

  private ResponseDiscountDTO convertToDto(Discount discount) {
    ResponseDiscountDTO getDto = new ResponseDiscountDTO();

    getDto.setId(discount.getId());
    getDto.setName(discount.getName());
    getDto.setDescription(discount.getDescription());
    getDto.setCode(discount.getCode());
    getDto.setValue(discount.getValue()); 
    getDto.setMinPurchaseAmount(discount.getMinPurchaseAmount()); 
    getDto.setMaxDiscountAmount(discount.getMaxDiscountAmount()); 
    getDto.setStoreName(discount.getStore().getName()); 
    getDto.setProductName(discount.getProduct().getName());

    return getDto;
  }
  
}
