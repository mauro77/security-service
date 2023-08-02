package com.nisum.securityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.securityservice.dto.phone.PhoneRequest;
import com.nisum.securityservice.dto.user.UpdateUserRequest;
import com.nisum.securityservice.dto.user.UserRequest;
import com.nisum.securityservice.model.User;
import com.nisum.securityservice.repository.UserRepository;
import com.nisum.securityservice.testbuilders.UserTestBuilder;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String USER_PATH = "/users";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldFetchAllUsers() throws Exception {
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("test").password("pass123456").email("emailtest@domain.com").isActive(true).build();
        User savedUser = userRepository.save(user);
        this.mockMvc.perform(get(USER_PATH)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", Is.is(savedUser.getName())))
                .andExpect(jsonPath("$[0].email", Is.is(savedUser.getEmail())));
    }

    @Test
    void shouldFetchOneUserByUuid() throws Exception {
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("test").password("pass123456").email("emailtest@domain.com").isActive(true).build();
        User savedUser = userRepository.save(user);
        this.mockMvc.perform(get(USER_PATH + "/{uuid}", user.getUuid().toString())).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Is.is(savedUser.getName())));
    }

    @Test
    void shouldGetErrorWhenFetchBagById() throws Exception {
        this.mockMvc.perform(get(USER_PATH + "/{uuid}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        PhoneRequest phoneRequest = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        UserRequest request = UserRequest.builder()
                .name("test").password("Pass123456").email("emailtest5@domain.com").isActive(true).phones(List.of(phoneRequest)).build();

        this.mockMvc.perform(post(USER_PATH).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Is.is(request.getName())));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("test").password("pass123456").email("emailtest@domain.com").isActive(true).build();
        User savedUser = userRepository.save(user);
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("test").build();

        this.mockMvc.perform(patch(USER_PATH + "/{uuid}", savedUser.getUuid().toString()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Is.is(request.getName())));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("test").password("pass123456").email("emailtest@domain.com").isActive(true).build();
        User savedUser = userRepository.save(user);

        this.mockMvc.perform(delete(USER_PATH + "/{uuid}", savedUser.getUuid().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUserNotFound() throws Exception {
        this.mockMvc.perform(delete(USER_PATH + "/{uuid}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());
    }
}