package com.finpro.grocery.address.controller;

import com.finpro.grocery.address.dto.AddressRequestDTO;
import com.finpro.grocery.address.dto.AddressResponseDTO;
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
    public ApiResponse<Pagination<AddressResponseDTO>> getCustomerAddresses(@RequestParam(value = "addressName",required = false) String addressName,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,
                                                                            @RequestParam(defaultValue = "id") String sortBy,
                                                                            @RequestParam(defaultValue = "asc") String sortDir){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.getCustomerAddresses(currentUserEmail, addressName, page, size, sortBy, sortDir));
    }

    @PostMapping("/create")
    public ApiResponse<Address> createAddress(@RequestBody AddressRequestDTO addressRequestDTO){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.createAddress(currentUserEmail, addressRequestDTO));
    }

    @PutMapping("/{id}")
    public ApiResponse<Address> updateAddress(@PathVariable("id") Long id, @RequestBody AddressRequestDTO addressRequestDTO){
        return new ApiResponse<>("OK", "200", addressService.updateAddress(id, addressRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteAddress(@PathVariable("id") Long id){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");
        addressService.deleteAddress(currentUserEmail, id);

        return new ApiResponse<>("OK", "200", "Address deleted successfully");
    }

    @PutMapping("/change-primary-address/{id}")
    public ApiResponse<AddressResponseDTO> changePrimaryAddress(@PathVariable("id") Long id){
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.switchPrimaryAddress(currentUserEmail, id));
    }

    @GetMapping("/primary")
    public ApiResponse<Address> getPrimaryAddress() {
        var claims = Claims.getClaimsFromJwt();
        String currentUserEmail = (String) claims.get("sub");

        return new ApiResponse<>("OK", "200", addressService.getPrimaryAddress(currentUserEmail));
    }

    @GetMapping("/{id}")
    public ApiResponse<AddressResponseDTO> getAddress(@PathVariable Long id) {
        return new ApiResponse<>("OK", "200", addressService.getAddress(id));
    }
}
