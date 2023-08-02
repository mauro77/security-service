package com.nisum.securityservice.service;


import com.nisum.securityservice.dto.phone.PhoneDto;
import com.nisum.securityservice.dto.phone.PhoneMapper;
import com.nisum.securityservice.dto.phone.PhoneRequest;
import com.nisum.securityservice.exception.ResourceNotFoundException;
import com.nisum.securityservice.model.Phone;
import com.nisum.securityservice.model.User;
import com.nisum.securityservice.repository.PhoneRepository;
import com.nisum.securityservice.service.impl.PhoneServiceImpl;
import com.nisum.securityservice.testbuilders.PhoneTestBuilder;
import com.nisum.securityservice.testbuilders.UserTestBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {
    @InjectMocks
    private PhoneServiceImpl phoneService;
    @Mock
    private PhoneRepository repository;
    @Mock
    private PhoneMapper phoneMapper;


    @Test
    void testCreateOrUpdateSuccess() {
        UUID phoneUuid1 = UUID.randomUUID();
        UUID phoneUuid2 = UUID.randomUUID();
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUser").password("testPass123456").email("emailtest5@domain.com").isActive(true).build();
        PhoneRequest phoneRequest1 = PhoneRequest.builder().uuid(phoneUuid1).cityCode("01").number("123456").countryCode("02").build();
        Phone phone1 = PhoneTestBuilder.builder().uuid(phoneRequest1.getUuid()).cityCode(phoneRequest1.getCityCode()).number(phoneRequest1.getNumber()).countryCode(phoneRequest1.getCountryCode()).
                build();
        PhoneDto phoneDto1 = PhoneDto.builder().uuid(phoneRequest1.getUuid()).cityCode(phoneRequest1.getCityCode()).countryCode(phoneRequest1.getCountryCode()).number(phoneRequest1.getNumber()).build();
        PhoneRequest phoneRequest2 = PhoneRequest.builder().cityCode("01").number("123456").countryCode("02").build();
        Phone phone2 = PhoneTestBuilder.builder().uuid(phoneUuid2).cityCode(phoneRequest2.getCityCode()).number(phoneRequest2.getNumber()).countryCode(phoneRequest2.getCountryCode()).build();
        PhoneDto phoneDto2 = PhoneDto.builder().uuid(phoneUuid2).cityCode(phoneRequest2.getCityCode()).countryCode(phoneRequest2.getCountryCode()).number(phoneRequest2.getNumber()).build();

        Mockito.when(repository.findById(phoneUuid1)).thenReturn(Optional.of(phone1));
        Mockito.when(repository.save(phone1)).thenReturn(phone1);
        Mockito.when(phoneMapper.toDto(phone1)).thenReturn(phoneDto1);

        Mockito.when(phoneMapper.createFromRequest(phoneRequest2)).thenReturn(phone2);
        Mockito.when(repository.save(phone2)).thenReturn(phone2);
        Mockito.when(phoneMapper.toDto(phone2)).thenReturn(phoneDto2);


        phoneService.saveOrUpdateForUser(List.of(phoneRequest1, phoneRequest2), user);

        Mockito.verify(phoneMapper).toDto(phone1);
        Mockito.verify(phoneMapper).toDto(phone2);
    }

    @Test
    void testDeleteByUuidNotfound() {
        UUID phoneUuid1 = UUID.randomUUID();
        User user = UserTestBuilder.builder().uuid(UUID.randomUUID()).name("testUser").password("testPass123456").email("emailtest5@domain.com").isActive(true).build();
        PhoneRequest phoneRequest1 = PhoneRequest.builder().uuid(phoneUuid1).cityCode("01").number("123456").countryCode("02").build();
        Mockito.when(repository.findById(phoneUuid1)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> phoneService.saveOrUpdateForUser(List.of(phoneRequest1), user));
    }
}