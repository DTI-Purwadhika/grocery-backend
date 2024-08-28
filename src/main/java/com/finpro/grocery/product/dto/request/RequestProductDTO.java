package com.finpro.grocery.product.dto.request;

import com.finpro.grocery.product.entity.ProductImage;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductDTO {

  @Size(max = 100, message = "Product name cannot exceed 100 characters")
  private String name;
  private String description;
  private String category;
  @Digits(integer = 12, fraction = 2, message = "Invalid price format")
  private BigDecimal price;
  private List<ProductImage> images;

}
