package com.finpro.grocery.address.service;

import com.finpro.grocery.address.dto.AddressRequestDTO;
import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.share.pagination.Pagination;

public interface AddressService {
    public Address createAddress(String customerEmail, AddressRequestDTO addressRequestDTO);

    public Address updateAddress(Long addressId, AddressRequestDTO addressRequestDTO);

    public Address switchPrimaryAddress(String customerEmail, Long addressId);

    public void deleteAddress(String customerEmail, Long addressId);

    public Pagination<Address> getAddressesByUserId(String customerEmail, String addressName, int page, int size, String sortBy, String sortDir);
}
