package com.nisum.securityservice.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing Users table
 *
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 **/
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseModel {

    private String name;

    private String email;

    private String password;

    private String token;

    @Column(name = "is_active")
    private Boolean isActive = true;


    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "user")
    @Setter(AccessLevel.PRIVATE)
    private List<Phone> phones = new ArrayList<>();

}
