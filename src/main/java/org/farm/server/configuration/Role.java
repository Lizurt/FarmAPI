package org.farm.server.configuration;

import java.util.stream.Stream;

/**
 * A role (authority) of a user. This affects the possibility of users
 * with or without the role to access server resources
 */
public enum Role {
    FARMER(0),
    ADMIN(1);

    private final int role;

    Role(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public static Role of(int role) {
        return Stream.of(Role.values())
                .filter(p -> p.getRole() == role)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
