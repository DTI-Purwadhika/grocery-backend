package com.finpro.grocery.store.dto;

import lombok.Data;
@Data
public class StoreRequestDTO {
    private String name;
    private String address;
    private Long cityId;
    private String postcode;
    private Float lat;
    private Float lng;
}
