package com.finpro.grocery.city.service.impl;

import com.finpro.grocery.city.dto.CityDTO;
import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.city.repository.CityRepository;
import com.finpro.grocery.city.service.CityService;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    @Override
    public City getCity(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City not found"));

    }

    @Override
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll().stream().map(CityDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public CityDTO getCityById(Long id){
        City city = cityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("City not found"));
        return CityDTO.toDTO(city);
    }
}
