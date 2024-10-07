package com.finpro.grocery.address.controller;

import com.cloudinary.Api;
import com.finpro.grocery.address.dto.AddressRequestDTO;
import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.address.service.AddressService;
import com.finpro.grocery.auth.helper.Claims;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.share.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @GetMapping("")
    public ApiResponse<Pagination<Address>> getAddressesByUserId(@RequestParam(value = "addressName",required = false) String addressName,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortDir){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.getAddressesByUserId(currentUserEmail, addressName, page, size, sortBy, sortDir));
    }

    @PostMapping("/create")
    public ApiResponse<Address> createAddress(@RequestBody AddressRequestDTO addressRequestDTO){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.createAddress(currentUserEmail, addressRequestDTO));
    }

    @PutMapping("/{id}")
    public ApiResponse<Address> updateAddress(@PathVariable Long id, @RequestBody AddressRequestDTO addressRequestDTO){
        return new ApiResponse<>("OK", "200", addressService.updateAddress(id, addressRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteAddress(@PathVariable Long id){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");
        addressService.deleteAddress(currentUserEmail, id);

        return new ApiResponse<>("OK", "200", "Address deleted successfully");
    }

    @PutMapping("/change-primary-address/{id}")
    public ApiResponse<Address> changePrimaryAddress(@PathVariable Long id){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.switchPrimaryAddress(currentUserEmail, id));
    }
}
