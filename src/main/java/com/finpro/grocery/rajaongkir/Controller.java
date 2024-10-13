package com.finpro.grocery.rajaongkir;

import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rajaongkir")
public class Controller {
    private RajaOngkirService rajaOngkirService;

    public Controller(RajaOngkirService rajaOngkirService){
        this.rajaOngkirService = rajaOngkirService;
    }

    @GetMapping("")
    public ApiResponse<?> getShipping(@RequestParam String origin, @RequestParam String destination, @RequestParam int weight, @RequestParam String courier){
        return new ApiResponse<>("OK", "200", rajaOngkirService.getShippingCost(origin, destination, weight, courier));
    }
}
