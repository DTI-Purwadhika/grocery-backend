package com.finpro.grocery.shipping.controller;

import com.finpro.grocery.auth.helper.Claims;
import com.finpro.grocery.share.response.ApiResponse;
import com.finpro.grocery.shipping.dto.ShippingDTO;
import com.finpro.grocery.shipping.service.ShippingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {
    private final ShippingService shippingService;
    public ShippingController(ShippingService shippingService){
        this.shippingService = shippingService;
    }

    @GetMapping("")
    public ApiResponse<List<ShippingDTO>> getShippingData(){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", shippingService.getAllShippings(currentUserEmail));
    }
}
