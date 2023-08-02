/**
 * 
 */
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
public class AuthTokenData implements Serializable {
	private static final long serialVersionUID = 8088115583723309849L;

	private String name;
	
	private String email;
	
}
