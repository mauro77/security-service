package com.nisum.securityservice.dto.phone;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    private String number;

    private String cityCode;

    private String countryCode;
}
