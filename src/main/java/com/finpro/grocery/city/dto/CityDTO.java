package com.finpro.grocery.city.dto;

import com.finpro.grocery.city.entity.City;
import lombok.Data;

@Data
public class CityDTO {
    private Long id;
    private String name;

    public static CityDTO toDTO(City city){
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());

        return cityDTO;
    }
}
