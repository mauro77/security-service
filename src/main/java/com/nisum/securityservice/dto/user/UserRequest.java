package com.nisum.securityservice.dto.user;

import com.nisum.securityservice.dto.phone.PhoneRequest;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;

    private String email;

    private String password;

    private List<PhoneRequest> phones = new ArrayList<>();
}
