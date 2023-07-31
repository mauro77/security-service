package com.nisum.securityservice.service.impl;


import com.nisum.securityservice.dto.phone.PhoneDto;
import com.nisum.securityservice.dto.security.AuthTokenData;
import com.nisum.securityservice.dto.user.UserDto;
import com.nisum.securityservice.dto.user.UserMapper;
import com.nisum.securityservice.dto.user.UserRequest;
import com.nisum.securityservice.model.User;
import com.nisum.securityservice.repository.UserRepository;
import com.nisum.securityservice.service.PhoneService;
import com.nisum.securityservice.service.UserService;
import com.nisum.securityservice.shared.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

/**
 * Implementation class for UserService
 *
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 **/
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PhoneService phoneService;

    private UserMapper userMapper;


    @Autowired
    public UserServiceImpl(UserRepository repository, UserMapper userMapper, PhoneService phoneService) {
        this.userRepository = repository;
        this.userMapper = userMapper;
        this.phoneService = phoneService;
    }

    /**
     * Create and persist a new user
     *
     * @param userRequest - request representing a new User
     * @return UserDto
     */
    @Override
    @Transactional
    public UserDto create(UserRequest userRequest) {
        var user = userMapper.toModel(userRequest);
        String jwtToken = JwtUtils.generateTokenAuth(AuthTokenData.builder().name(user.getName()).email(user.getEmail()).build());
        user.setToken(jwtToken);
        User savedUser = userRepository.save(user);
        List<PhoneDto> phonesRsponselist = phoneService.saveOrUpdateForUser(userRequest.getPhones(), savedUser);
        UserDto dto = userMapper.toDto(savedUser);
        dto.setPhones(phonesRsponselist);
        return dto;
    }

    /**
     * List all existing users
     *
     * @return List<UserDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }

    /**
     * Find a user by its uuid
     *
     * @param uuid - User uuid
     * @return UserDto
     */
    @Override
    @Transactional(readOnly = true)
    public UserDto findByUuid(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toDto(user);
    }
}
