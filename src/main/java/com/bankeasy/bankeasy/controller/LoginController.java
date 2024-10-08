package com.bankeasy.bankeasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bankeasy.bankeasy.services.LoginService;
import com.bankeasy.bankeasy.reqres.LoginRequest;
import com.bankeasy.bankeasy.reqres.LoginResponse;


@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
    @Autowired
	private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        
        LoginResponse response = loginService.login(loginRequest);
       
        if (response.isHasError()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}