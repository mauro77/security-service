package com.nisum.securityservice.controller;

import com.nisum.securityservice.dto.user.UserDto;
import com.nisum.securityservice.dto.user.UserRequest;
import com.nisum.securityservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 */
@RestController
@RequestMapping("/users")
public class UserController {


    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserRequest userRequest) {
        UserDto userDto = service.create(userRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @GetMapping("{uuid}")
    public ResponseEntity<UserDto> getById(@PathVariable("uuid") UUID userUuid) {
        UserDto userDto = service.findByUuid(userUuid);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
