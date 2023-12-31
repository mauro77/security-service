package com.nisum.securityservice.repository;

import com.nisum.securityservice.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, UUID> {
}