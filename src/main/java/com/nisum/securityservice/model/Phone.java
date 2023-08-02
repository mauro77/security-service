package com.nisum.securityservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Entity representing Phones table
 *
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 **/
@Entity
@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Phone extends BaseModel {

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private User user;
}
