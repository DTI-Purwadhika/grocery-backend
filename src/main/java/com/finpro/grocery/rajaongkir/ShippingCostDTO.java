package com.finpro.grocery.rajaongkir;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShippingCostDTO {
    private String courier;
    private int cost;
}
