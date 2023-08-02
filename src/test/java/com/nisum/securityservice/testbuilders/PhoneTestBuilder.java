package com.nisum.securityservice.testbuilders;

import com.nisum.securityservice.model.Phone;
import com.nisum.securityservice.model.User;
import lombok.Builder;

import java.util.UUID;

public class PhoneTestBuilder {

    @Builder
    public static Phone phone(UUID uuid, String number, String countryCode,
                              String cityCode, User user) {
        Phone phone = new Phone();
        phone.setUuid(uuid);
        phone.setNumber(number);
        phone.setCityCode(cityCode);
        phone.setCountryCode(countryCode);
        phone.setUser(user);
        return phone;
    }

    public static class PhoneBuilder {
        private UUID uuid = UUID.randomUUID();
        private String number = "defaultNumber";

        private String cityCode = "defaultCode";

        private String countryCode = "defaultCode";

        private User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).build();
    }
}
