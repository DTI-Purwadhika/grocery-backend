package com.finpro.grocery.address.dto;

import lombok.Data;

@Data
public class AddressRequestDTO {
    private String addressName;
    private String postcode;
    private Long cityId;
    private Float lat;
    private Float lng;
}
