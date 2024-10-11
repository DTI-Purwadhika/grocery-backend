package com.finpro.grocery.shipping.repository;

import com.finpro.grocery.city.entity.City;
import com.finpro.grocery.shipping.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    List<Shipping> findByOriginAndDestinationAndWeight(City origin, City destination, int weight);
}
