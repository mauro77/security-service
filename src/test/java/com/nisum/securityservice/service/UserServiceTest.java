package com.nisum.securityservice.service;


import com.nisum.securityservice.dto.phone.PhoneDto;
import com.nisum.securityservice.dto.phone.PhoneRequest;
import com.nisum.securityservice.dto.security.AuthTokenData;
import com.nisum.securityservice.dto.user.UpdateUserRequest;
import com.nisum.securityservice.dto.user.UserMapper;
import com.nisum.securityservice.dto.user.UserRequest;
import com.nisum.securityservice.exception.ResourceNotFoundException;
import com.nisum.securityservice.exception.UnprocessableEntityException;
import com.nisum.securityservice.model.User;
import com.nisum.securityservice.repository.UserRepository;
import com.nisum.securityservice.service.impl.UserServiceImpl;
import com.nisum.securityservice.shared.JwtUtils;
import com.nisum.securityservice.testbuilders.UserTestBuilder;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository repository;

    @Mock
    private PhoneService phoneService;
    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setup() {
        userService.setEmailRegex("^(.+)@(.+)$");
        userService.setPasswordRegex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d\\w\\W]{8,}$");
    }
    @BeforeAll
    static void generalSetup() {
        MockedStatic<JwtUtils> jwtUtils = Mockito.mockStatic(JwtUtils.class);
        jwtUtils.when(() -> JwtUtils.generateTokenAuth(Mockito.any(AuthTokenData.class))).thenReturn("token");
    }


    @Test
    void testCreatingSuccess() {
        PhoneRequest phoneRequest = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        UserRequest request = UserRequest.builder()
                .name("testUser").password("Pass123456").email("emailtest5@domain.com").isActive(true).phones(List.of(phoneRequest)).build();
        PhoneDto phoneDto = PhoneDto.builder().uuid(UUID.randomUUID()).cityCode(phoneRequest.getCityCode()).countryCode(phoneRequest.getCountryCode()).number(phoneRequest.getNumber()).build();
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUser").password("testPass123456").email("emailtest5@domain.com").isActive(true).build();

        Mockito.when(userMapper.toModel(Mockito.any(UserRequest.class))).thenReturn(user);
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(phoneService.saveOrUpdateForUser(request.getPhones(), user)).thenReturn(List.of(phoneDto));


        userService.create(request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).save(userCaptor.capture());
        Mockito.verify(userMapper).toDto(user);

        assertEquals(request.getName(), userCaptor.getValue().getName());
        assertEquals(request.getEmail(), userCaptor.getValue().getEmail());

    }

    @Test
    void testCreatingEmailExists() {
        PhoneRequest phoneRequest = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        UserRequest request = UserRequest.builder()
                .name("testUser").password("Pass123456").email("emailtest5@domain.com").isActive(true).phones(List.of(phoneRequest)).build();

        Mockito.when(repository.existsByEmail(request.getEmail())).thenReturn(true);

        Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.create(request));

    }

    @Test
    void testCreatingInvalidEmail() {
        PhoneRequest phoneRequest = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        UserRequest request = UserRequest.builder()
                .name("testUser").password("Pass123456").email("emailtest5").isActive(true).phones(List.of(phoneRequest)).build();

        Mockito.when(repository.existsByEmail(request.getEmail())).thenReturn(false);

        Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.create(request));

    }

    @Test
    void testCreatingInvalidPass() {
        PhoneRequest phoneRequest = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        UserRequest request = UserRequest.builder()
                .name("testUser").password("pass123456").email("emailtest5@domain.com").isActive(true).phones(List.of(phoneRequest)).build();

        Mockito.when(repository.existsByEmail(request.getEmail())).thenReturn(false);

        Assertions.assertThrows(UnprocessableEntityException.class, () -> userService.create(request));

    }
    @Test
    void testUpdatingSuccess() {
        UUID userUuid = UUID.randomUUID();
        PhoneRequest phoneRequest = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        UpdateUserRequest request = UpdateUserRequest.builder()
                .name("testUserUpdate").password("Pass123456A").email("emailtest5A@domain.com").isActive(true).phones(List.of(phoneRequest)).build();
        PhoneDto phoneDto = PhoneDto.builder().uuid(UUID.randomUUID()).cityCode(phoneRequest.getCityCode()).countryCode(phoneRequest.getCountryCode()).number(phoneRequest.getNumber()).build();
        User user = UserTestBuilder.builder().uuid(userUuid).name("testUserUpdate").password("testPass123456").email("emailtest5A@domain.com").isActive(true).build();

        Mockito.when(repository.findById(user.getUuid())).thenReturn(Optional.of(user));
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(phoneService.saveOrUpdateForUser(request.getPhones(), user)).thenReturn(List.of(phoneDto));

        userService.update(userUuid, request);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(repository).save(userCaptor.capture());
        Mockito.verify(userMapper).toDto(user);

        assertEquals(request.getName(), userCaptor.getValue().getName());
        assertEquals(request.getEmail(), userCaptor.getValue().getEmail());

    }


    @Test
    void testGetAll() {

        User user1 = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUsera").password("testPass123456").email("userEmaila@domain.com").isActive(true).build();
        User user2 = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUserb").password("testPass123456").email("userEmailb@domain.com").isActive(true).build();

        List<User> userList = Lists.newArrayList(user1, user2);
        Mockito.when(repository.findAll()).thenReturn(userList);

        userService.getAll();

        Mockito.verify(repository).findAll();
        Mockito.verify(userMapper).toDto(userList);
    }

    @Test
    void testFindByIdSuccess() {
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUsera").password("testPass123456").email("userEmaila@domain.com").isActive(true).build();
        Mockito.when(repository.findById(user.getUuid())).thenReturn(Optional.of(user));
        userService.findByUuid(user.getUuid());
        Mockito.verify(repository).findById(user.getUuid());
        Mockito.verify(userMapper).toDto(user);

    }

    @Test
    void testFindByIdNotfound() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(repository.findById(uuid)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findByUuid(uuid));
    }

    @Test
    void testDeleteByUuidSuccess() {
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUsera").password("testPass123456").email("userEmaila@domain.com").isActive(true).build();
        Mockito.when(repository.existsById(user.getUuid())).thenReturn(true);
        userService.delete(user.getUuid());
        Mockito.verify(repository).existsById(user.getUuid());
        Mockito.verify(repository).deleteById(user.getUuid());
    }

    @Test
    void testDeleteByUuidNotfound() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(repository.existsById(uuid)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.delete(uuid));
    }
}