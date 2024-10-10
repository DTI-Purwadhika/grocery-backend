package com.finpro.grocery.shipping.dto;

import com.finpro.grocery.shipping.entity.Shipping;
import lombok.Data;

@Data
public class ShippingDTO {
    private Long id;
    private String courier;
    private int cost;

    public static ShippingDTO toDto(Shipping shipping){
        ShippingDTO shippingDTO = new ShippingDTO();
        shippingDTO.setId(shipping.getId());
        shippingDTO.setCourier(shipping.getCourier());
        shippingDTO.setCost(shipping.getCost());

        return shippingDTO;
    }
}
