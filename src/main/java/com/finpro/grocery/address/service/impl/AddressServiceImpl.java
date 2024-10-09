package com.finpro.grocery.address.service.impl;

import com.finpro.grocery.address.dto.AddressRequestDTO;
import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.address.repository.AddressRepository;
import com.finpro.grocery.address.service.AddressService;
import com.finpro.grocery.address.specification.AddressSpecification;
import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.city.service.CityService;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.share.pagination.Pagination;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final CityService cityService;
    public AddressServiceImpl(AddressRepository addressRepository, UserService userService, CityService cityService){
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.cityService = cityService;
    }
    @Override
    public Address createAddress(String customerEmail, AddressRequestDTO addressRequestDTO) {
        User customer = userService.getUserByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        City city = cityService.getCity(addressRequestDTO.getCityId());
        Address address = new Address();

        address.setAddressName(addressRequestDTO.getAddressName());
        address.setPostcode(addressRequestDTO.getPostcode());
        address.setLatitude(addressRequestDTO.getLat());
        address.setLongitude(addressRequestDTO.getLng());
        address.setUser(customer);
        address.setCity(city);

        List<Address> customerAddresses = addressRepository.findByUser(customer);
        boolean hasPrimaryAddress = customerAddresses.stream().anyMatch(Address::getIsPrimary);

        if(!hasPrimaryAddress){
            address.setIsPrimary(true);
        }

        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public Address updateAddress(Long id, AddressRequestDTO addressRequestDTO) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        City city = cityService.getCity(addressRequestDTO.getCityId());

        address.setAddressName(addressRequestDTO.getAddressName());
        address.setPostcode(addressRequestDTO.getPostcode());
        address.setLatitude(addressRequestDTO.getLat());
        address.setLongitude(addressRequestDTO.getLng());
        address.setCity(city);

        return addressRepository.save(address);
    }

    @Override
    public Address switchPrimaryAddress(String customerEmail, Long addressId) {
        User customer = userService.getUserByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Address initialPrimaryAddress = addressRepository.findByUserAndIsPrimaryTrue(customer).orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        initialPrimaryAddress.setIsPrimary(false);
        addressRepository.save(initialPrimaryAddress);

        Address updatedPrimaryAddress = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        updatedPrimaryAddress.setIsPrimary(true);

        return addressRepository.save(updatedPrimaryAddress);
    }

    @Transactional
    @Override
    public void deleteAddress(String customerEmail, Long addressId) {
        Address deletedAddress = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        addressRepository.delete(deletedAddress);

        User customer = userService.getUserByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Address> customerAddresses = addressRepository.findByUser(customer);
        boolean hasPrimaryAddress = customerAddresses.stream().anyMatch(Address::getIsPrimary);

        if(!hasPrimaryAddress){
            Address firstAddress = customerAddresses.getFirst();
            firstAddress.setIsPrimary(true);
            addressRepository.save(firstAddress);
        }
    }

    @Override
    public Pagination<Address> getAddressesByUserId(String customerEmail, String addressName, int page, int size, String sortBy, String sortDir) {
        User customer = userService.getUserByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Specification<Address> addressSpecification = Specification.where(AddressSpecification.byAddressName(addressName)
                .and(AddressSpecification.byUserId(customer.getId())));

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Address> addresses = addressRepository.findAll(addressSpecification, pageable);

        return new Pagination<>(
                addresses.getTotalPages(),
                addresses.getTotalElements(),
                addresses.isFirst(),
                addresses.isLast(),
                addresses.getContent()
        );
    }
}
