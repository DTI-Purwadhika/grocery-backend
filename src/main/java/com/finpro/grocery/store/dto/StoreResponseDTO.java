package com.finpro.grocery.store.dto;

import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.store.entity.Store;
import lombok.Data;

@Data
public class StoreResponseDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String postcode;
    private Float lat;
    private Float lng;

    public static StoreResponseDTO toDto(Store store){
        StoreResponseDTO storeResponseDTO = new StoreResponseDTO();
        storeResponseDTO.setId(store.getId());
        storeResponseDTO.setName(store.getName());
        storeResponseDTO.setAddress(store.getAddress());
        storeResponseDTO.setCity(store.getCity().getName());
        storeResponseDTO.setPostcode(store.getPostcode());
        storeResponseDTO.setLat(store.getLatitude());
        storeResponseDTO.setLng(store.getLongitude());

        return storeResponseDTO;
    }
}
