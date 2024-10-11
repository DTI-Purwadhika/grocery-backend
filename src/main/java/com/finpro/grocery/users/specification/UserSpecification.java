package com.finpro.grocery.users.specification;

import com.finpro.grocery.store.entity.Store;
import com.finpro.grocery.users.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> byName(String name) {
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

    public static Specification<User> byRole(User.UserRole role) {
        return ((root, query, builder)-> {
            if (role == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("role"), role);
        });
    }
}
