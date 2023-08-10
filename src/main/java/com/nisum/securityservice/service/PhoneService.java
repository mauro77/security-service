package com.nisum.securityservice.service;

import com.nisum.securityservice.dto.phone.PhoneDto;
import com.nisum.securityservice.dto.phone.PhoneRequest;
import com.nisum.securityservice.model.User;

import java.util.List;

/**
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 */
public interface PhoneService {
    List<PhoneDto> saveOrUpdateForUser(List<PhoneRequest> createOrUpdateRequest, User user);
}
