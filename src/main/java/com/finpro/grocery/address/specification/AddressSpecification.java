package com.finpro.grocery.address.specification;

import com.finpro.grocery.address.entity.Address;
import com.finpro.grocery.users.entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {
    public static Specification<Address> byAddressName(String addressName) {
        return ((root, query, builder) -> {
            if (addressName == null) {
                return builder.conjunction();
            }
            if (addressName.isEmpty()) {
                return builder.equal(root.get("addressName"), "");
            }
            return builder.like(builder.lower(root.get("addressName")), "%" + addressName.toLowerCase() + "%");
        });
    }

    public static Specification<Address> byUserId(Long userId) {
        return ((root, query, builder)-> {
            if (userId == null) {
                return builder.conjunction();
            }
            Join<Address, User> userJoin = root.join("user", JoinType.LEFT);
            return builder.equal(userJoin.get("id"), userId);
        });
    }
}
