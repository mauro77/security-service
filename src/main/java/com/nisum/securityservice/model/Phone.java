package com.nisum.securityservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Phone extends BaseModel {

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
