package com.nisum.securityservice.service;

import com.nisum.securityservice.dto.user.UserDto;
import com.nisum.securityservice.dto.user.UserRequest;

import java.util.List;
import java.util.UUID;

/**
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 */
public interface UserService {
    UserDto create(UserRequest userRequest);

    List<UserDto> getAll();

    UserDto findByUuid(UUID uuid);
}
