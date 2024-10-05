package com.finpro.grocery.city.service;

import com.finpro.grocery.city.dto.CityDTO;
import com.finpro.grocery.city.entity.City;

import java.util.List;

public interface CityService {
    public City getCity(Long id);
    public List<CityDTO> getAllCities();
    public CityDTO getCityById(Long id);
}
