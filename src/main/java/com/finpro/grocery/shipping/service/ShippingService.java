package com.finpro.grocery.shipping.service;

import com.finpro.grocery.rajaongkir.ShippingCostDTO;
import com.finpro.grocery.shipping.dto.ShippingDTO;

import java.util.List;
import java.util.Map;

public interface ShippingService {
    public List<ShippingDTO> getAllShippings(String email);
    public int getShippingCost(Long id);
    public void deleteShipping(Long id);
}
