package com.nisum.securityservice.dto.security;

import lombok.*;

import java.io.Serializable;

/**
 * @author mauricio Hincapie Monsalve
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

  private static final long serialVersionUID = -6275388923361889606L;

  private String name;

  private String password;

}
