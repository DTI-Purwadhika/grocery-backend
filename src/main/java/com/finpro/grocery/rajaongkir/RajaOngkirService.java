package com.finpro.grocery.rajaongkir;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.city.repository.CityRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RajaOngkirService {
    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    @Value("${rajaongkir.api.key}")
    private String apiKey;
    private final static String BASE_URL = "https://api.rajaongkir.com/starter";
    public RajaOngkirService(RestTemplate restTemplate, CityRepository cityRepository){
        this.restTemplate = restTemplate;
        this.cityRepository = cityRepository;
    }

    public List<RajaOngkirCityResult> getAllCities(){
        String url = BASE_URL + "/city";
        HttpHeaders headers = new HttpHeaders();
        headers.set("key", apiKey);

        HttpEntity<String> entity =  new HttpEntity<>(headers);
        ResponseEntity<RajaOngkirResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, RajaOngkirResponse.class);

        if (response.getBody() != null && response.getBody().getRajaOngkirData() != null){
            List<RajaOngkirCityResult> results = response.getBody().getRajaOngkirData().getResults();

            if(!results.isEmpty()){
                List<City> cities = results.stream().map(this::mapToCity).toList();
                cityRepository.saveAll(cities);
            }

            return results;
        }
        return null;
    }

    public City mapToCity(RajaOngkirCityResult result) {
        City city = new City();
        city.setId(Long.parseLong(result.getCityId()));
        city.setName(result.getCityName());
        return city;
    }

    @Data
    public static class RajaOngkirResponse {
        @JsonProperty("rajaongkir")
        private RajaOngkirData rajaOngkirData;
    }

    @Data
    public static class RajaOngkirData {
        @JsonProperty("results")
        private List<RajaOngkirCityResult> results;
    }
    @Data
    public static class RajaOngkirCityResult {
        @JsonProperty("city_id")
        private String cityId;

        @JsonProperty("city_name")
        private String cityName;
    }
}
