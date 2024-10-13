package com.finpro.grocery.address.dto;

import com.finpro.grocery.address.entity.Address;
import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long id;
    private String addressName;
    private String postcode;
    private String city;
    private Float lat;
    private Float lng;
    private Boolean isPrimary;

    public static AddressResponseDTO toDto(Address address){
        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(address.getId());
        addressResponseDTO.setAddressName(address.getAddressName());
        addressResponseDTO.setPostcode(address.getPostcode());
        addressResponseDTO.setCity(address.getCity().getName());
        addressResponseDTO.setLat(address.getLatitude());
        addressResponseDTO.setLng(address.getLongitude());
        addressResponseDTO.setIsPrimary(address.getIsPrimary());

        return addressResponseDTO;
    }
}
