package com.finpro.grocery.discount.service;

import java.math.BigDecimal;

import com.finpro.grocery.discount.entity.Discount;
import com.finpro.grocery.product.entity.Product;

public class ApplyDiscount {
  
  public BigDecimal applyDiscount(BigDecimal totalAmount, Product product, int quantity, Discount discount) {
    BigDecimal finalAmount = totalAmount;

    switch (discount.getType()) {
      case BOGO:
        finalAmount = applyBogoDiscount(product, quantity);
        break;
      case PERCENTAGE:
        finalAmount = applyPercentageDiscount(totalAmount, discount);
        break;
      case FLAT:
        finalAmount = applyFlatDiscount(totalAmount, discount);
        break;
    }

    return finalAmount;
  }

  private BigDecimal applyBogoDiscount(Product product, int quantity) {
    BigDecimal productPrice = product.getPrice();
    int eligiblePairs = quantity / 2;
    int fullPriceQuantity = quantity - eligiblePairs;
    return productPrice.multiply(new BigDecimal(fullPriceQuantity));
  }

  private BigDecimal applyPercentageDiscount(BigDecimal totalAmount, Discount discount) {
    BigDecimal discountAmount = BigDecimal.valueOf(discount.getValue()/100).multiply(totalAmount);
    return totalAmount.subtract(totalAmount.subtract(discountAmount));
  }

  private BigDecimal applyFlatDiscount(BigDecimal totalAmount, Discount discount) {
    return totalAmount.subtract(BigDecimal.valueOf(discount.getValue()));
  }

}
