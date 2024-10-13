package com.finpro.grocery.address.service;

import com.finpro.grocery.address.dto.AddressRequestDTO;
import com.finpro.grocery.address.dto.AddressResponseDTO;
import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.users.entity.User;

public interface AddressService {
    public Address createAddress(String customerEmail, AddressRequestDTO addressRequestDTO);

    public Address updateAddress(Long addressId, AddressRequestDTO addressRequestDTO);

    public Address getPrimaryAddress(String customerEmail);

    public AddressResponseDTO switchPrimaryAddress(String customerEmail, Long addressId);

    public void deleteAddress(String customerEmail, Long addressId);

    public Pagination<AddressResponseDTO> getCustomerAddresses(String customerEmail, String addressName, int page, int size, String sortBy, String sortDir);

    public AddressResponseDTO getAddress(Long id);
}
