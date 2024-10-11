package com.finpro.grocery.address.repository;

import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
    List<Address> findByUser(User user);
    Optional<Address> findByUserAndIsPrimaryTrue(User user);
}
