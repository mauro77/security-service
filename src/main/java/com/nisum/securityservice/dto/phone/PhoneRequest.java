package com.nisum.securityservice.dto.phone;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRequest {
    private UUID uuid;

    private String number;

    private String cityCode;

    private String countryCode;
}
