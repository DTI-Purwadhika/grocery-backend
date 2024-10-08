package com.finpro.grocery.store.specification;

import com.finpro.grocery.store.entity.Store;
import org.springframework.data.jpa.domain.Specification;

public class StoreSpecification {
    public static Specification<Store> byName(String name) {
        return ((root, query, builder) -> {
            if (name == null) {
                return builder.conjunction();
            }
            if (name.isEmpty()) {
                return builder.equal(root.get("name"), "");
            }
            return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        });
    }

    public static Specification<Store> byCity(String city) {
        return ((root, query, builder)-> {
            if (city == null) {
                return builder.conjunction();
            }
            return builder.equal(builder.lower(root.get("city")), city.toLowerCase());
        });
    }
}
