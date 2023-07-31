package com.nisum.securityservice.service.impl;


import com.nisum.securityservice.dto.phone.PhoneDto;
import com.nisum.securityservice.dto.phone.PhoneMapper;
import com.nisum.securityservice.dto.phone.PhoneRequest;
import com.nisum.securityservice.exception.ResourceNotFoundException;
import com.nisum.securityservice.model.Phone;
import com.nisum.securityservice.model.User;
import com.nisum.securityservice.repository.PhoneRepository;
import com.nisum.securityservice.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation class for UserService
 *
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 **/
@Service
public class PhoneServiceImpl implements PhoneService {

    private PhoneRepository phoneRepository;

    private PhoneMapper phoneMapper;


    @Autowired
    public PhoneServiceImpl(PhoneRepository phoneRepository, PhoneMapper phoneMapper) {
        this.phoneRepository = phoneRepository;
        this.phoneMapper = phoneMapper;
    }

    /**
     * Create or update a list of phones for a given user
     *
     * @param createOrUpdateRequest - request representing a list of phones to save or update
     * @return void
     */
    @Override
    @Transactional
    public List<PhoneDto> saveOrUpdateForUser(List<PhoneRequest> createOrUpdateRequest, User user) {
        return createOrUpdateRequest.stream().map(request -> {
            Phone phone = getForSaveOrUpdate(request);
            return phoneMapper.toDto(phone);
        }).collect(Collectors.toList());
    }

    private Phone getForSaveOrUpdate(PhoneRequest request) {
        if (request.getUuid() != null) {
            Phone foundPhone = phoneRepository.findById(request.getUuid()).orElseThrow(() -> new ResourceNotFoundException("Phone whit uuid" + request.getUuid().toString() + "not found"));
            phoneMapper.updateModel(request, foundPhone);
            return foundPhone;
        } else {
            return phoneMapper.createFromRequest(request);
        }
    }
}
