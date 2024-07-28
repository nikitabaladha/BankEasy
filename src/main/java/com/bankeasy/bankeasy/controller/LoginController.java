
package com.bankeasy.bankeasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bankeasy.bankeasy.services.LoginService;
import com.bankeasy.bankeasy.entities.LoginRequest;
import com.bankeasy.bankeasy.entities.LoginResponse;


@RestController
@RequestMapping("/api")
public class LoginController {
	
    @Autowired
	private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received signup request: " +loginRequest);
        LoginResponse response = loginService.login(loginRequest);
        System.out.println("Signup response: " + response);
        if (response.isHasError()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}