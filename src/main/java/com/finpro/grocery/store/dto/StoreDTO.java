package com.finpro.grocery.store.dto;

import com.finpro.grocery.store.entity.Store;
import lombok.Data;

@Data
public class StoreDTO {
    private String name;
    private String address;
    private String postcode;
    private Float lat;
    private Float lng;

    public Store toEntity(){
        Store store = new Store();
        store.setName(name);
        store.setAddress(address);
        store.setPostcode(postcode);
        store.setLatitude(lat);
        store.setLongitude(lng);

        return store;
    }

}
