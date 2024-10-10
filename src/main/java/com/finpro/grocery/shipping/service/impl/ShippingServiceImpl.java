package com.finpro.grocery.shipping.service.impl;

import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.address.service.AddressService;
import com.finpro.grocery.cart.service.ReadCart;
import com.finpro.grocery.city.service.CityService;
import com.finpro.grocery.rajaongkir.RajaOngkirService;
import com.finpro.grocery.rajaongkir.ShippingCostDTO;
import com.finpro.grocery.share.exception.ResourceNotFoundException;
import com.finpro.grocery.shipping.dto.ShippingDTO;
import com.finpro.grocery.shipping.entity.Shipping;
import com.finpro.grocery.shipping.repository.ShippingRepository;
import com.finpro.grocery.shipping.service.ShippingService;
import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.store.service.StoreService;
import com.finpro.grocery.users.entity.User;
import com.finpro.grocery.users.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShippingServiceImpl implements ShippingService {
    private final ShippingRepository shippingRepository;
    private final AddressService addressService;
    private final RajaOngkirService rajaOngkirService;
    private final ReadCart readCart;
    private final UserService userService;
    private final StoreService storeService;
    private final CityService cityService;
    public ShippingServiceImpl(AddressService addressService, RajaOngkirService rajaOngkirService, ReadCart readCart, UserService userService, StoreService storeService, ShippingRepository shippingRepository, CityService cityService){
        this.addressService = addressService;
        this.rajaOngkirService = rajaOngkirService;
        this.readCart = readCart;
        this.userService = userService;
        this.storeService = storeService;
        this.shippingRepository = shippingRepository;
        this.cityService = cityService;
    }
    @Transactional
    @Override
    public List<ShippingDTO> getAllShippings(String email) {
        User customer = userService.getUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Long userId = customer.getId();
        int totalWeight = readCart.getTotalWeight(userId);
        Address primaryAddress = addressService.getPrimaryAddress(email);
        Store nearestStore = storeService.getNearestStore(email);

        List<Shipping> shippingList = shippingRepository.findByOriginAndDestinationAndWeight(nearestStore.getCity(), primaryAddress.getCity(), totalWeight);

        if(shippingList.isEmpty()){
            List<ShippingCostDTO> shippingCostsAllCouriersList = rajaOngkirService.getShippingCostsAllCouriers(nearestStore.getCity().getId().toString(), primaryAddress.getCity().getId().toString(), totalWeight);
            List<ShippingDTO> shippings = new ArrayList<>();

            for(ShippingCostDTO shippingCost : shippingCostsAllCouriersList){
                Shipping shipping = new Shipping();
                shipping.setCourier(shippingCost.getCourier());
                shipping.setCost(shippingCost.getCost());
                shipping.setWeight(totalWeight);
                shipping.setOrigin(cityService.getCity(nearestStore.getCity().getId()));
                shipping.setDestination(cityService.getCity(primaryAddress.getCity().getId()));
                shippingRepository.save(shipping);

                shippings.add(ShippingDTO.toDto(shipping));
            }

            return shippings;
        }

        return shippingList.stream().map(ShippingDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public int getShippingCost(Long id) {
        Shipping shipping = shippingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shipping not found"));
        return shipping.getCost();
    }

    @Override
    public void deleteShipping(Long id) {
        shippingRepository.deleteById(id);
    }
}
