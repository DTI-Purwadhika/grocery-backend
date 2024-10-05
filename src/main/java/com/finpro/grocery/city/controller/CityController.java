package com.finpro.grocery.city.controller;

import com.cloudinary.Api;
import com.finpro.grocery.city.dto.CityDTO;
import com.finpro.grocery.city.service.CityService;
import com.finpro.grocery.rajaongkir.RajaOngkirService;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;
    private final RajaOngkirService rajaOngkirService;
    public CityController(CityService cityService, RajaOngkirService rajaOngkirService){
        this.cityService = cityService;
        this.rajaOngkirService = rajaOngkirService;
    }
    @GetMapping("")
    public ApiResponse<List<CityDTO>> getAllCities(){
        return new ApiResponse<>("OK", "200", cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ApiResponse<CityDTO> getCityById(@PathVariable Long id){
        return new ApiResponse<>("OK", "200", cityService.getCityById(id));
    }

    @GetMapping("/fetch-rajaongkir")
    public ApiResponse<List<RajaOngkirService.RajaOngkirCityResult>> fetchCities(){
        return new ApiResponse<>("OK", "200", rajaOngkirService.getAllCities());
    }

}
