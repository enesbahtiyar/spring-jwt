package com.eb.controller;

import com.eb.dto.RegisterRequest;
import com.eb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserJwtController
{
    @Autowired
    private UserService userService;

    //********** Register New User **********
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request)
    {
        userService.registerUser(request);

        String message = "user has been successfully saved";

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    //********** Login
}
