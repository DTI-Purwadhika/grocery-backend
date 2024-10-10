package com.finpro.grocery.rajaongkir;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.city.repository.CityRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CompletableFuture;
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

    public List<ShippingCostDTO> getShippingCostsAllCouriers(String origin, String destination, int weight){
        List<String> couriers = Arrays.asList("pos", "tiki", "jne");

        return couriers.stream().map(courier -> getShippingCost(origin, destination, weight, courier)).toList();
    }

    public ShippingCostDTO getShippingCost(String origin, String destination, int weight, String courier){
        String url = BASE_URL + "/cost";
        HttpHeaders headers = new HttpHeaders();
        headers.set("key", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String request = String.format("{ \"origin\":\"%s\", \"destination\":\"%s\", \"weight\":%d, \"courier\":\"%s\" }",
                origin, destination, weight, courier);

        HttpEntity<String> entity =  new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        Map<String, Object> rajaongkir = (Map<String, Object>) response.getBody().get("rajaongkir");
        List<Map<String, Object>> results = (List<Map<String, Object>>) rajaongkir.get("results");
        List<Map<String, Object>> costs = (List<Map<String, Object>>) results.get(0).get("costs");

        int lowestCost = costs.stream().flatMap(cost -> ((List<Map<String, Object>>) cost.get("cost")).stream())
                .mapToInt(cost -> (int) cost.get("value"))
                .min().orElse(0);

        return new ShippingCostDTO(courier, lowestCost);
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
