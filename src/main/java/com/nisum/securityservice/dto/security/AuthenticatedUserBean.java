package com.nisum.securityservice.dto.security;

import lombok.*;

import java.io.Serializable;

/**
 * @author mauricio Hincapie Monsalve
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUserBean implements Serializable {

    private static final long serialVersionUID = 3778142433810934735L;

    private String token;

    private String name;

    private String email;
}
