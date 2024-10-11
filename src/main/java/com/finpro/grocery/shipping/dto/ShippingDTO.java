package com.finpro.grocery.shipping.dto;

import com.finpro.grocery.city.service.CityService;
import com.finpro.grocery.shipping.entity.Shipping;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class ShippingDTO {
    private Long id;
    private String origin;
    private String destination;
    private String courier;
    private int cost;
    private String description;
    private String etd;

    public static ShippingDTO toDto(Shipping shipping){
        ShippingDTO shippingDTO = new ShippingDTO();
        shippingDTO.setId(shipping.getId());
        shippingDTO.setCourier(shipping.getCourier());
        shippingDTO.setCost(shipping.getCost());
        shippingDTO.setDescription(shipping.getDescription());
        shippingDTO.setEtd(shipping.getEtd());

        return shippingDTO;
    }
}
