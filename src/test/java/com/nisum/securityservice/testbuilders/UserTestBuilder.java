package com.nisum.securityservice.testbuilders;

import com.nisum.securityservice.model.Phone;
import com.nisum.securityservice.model.User;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserTestBuilder {
    @Builder
    public static User user(UUID uuid, String name, String email, String password, String token, Boolean isActive, LocalDateTime lastLogin, List<Phone> phones) {
        User user = new User();
        user.setUuid(uuid);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setToken(token);
        user.setIsActive(isActive);
        user.setLastLogin(lastLogin);
        if (phones != null) {
            user.setPhones(phones);
        }
        return user;
    }

    public static class UserBuilder {
        private UUID uuid = UUID.randomUUID();
        private String name = "default";

        private String email = "default@domain.com";

        private String password = "defaultPass123";

        private String token = "defaultToken";

        private Boolean isActive = true;

        private LocalDateTime lastLogin = LocalDateTime.now();

        private List<Phone> phones = null;
    }
}
