package com.bankeasy.bankeasy.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bankeasy.bankeasy.entities.SignupRequest;
import com.bankeasy.bankeasy.entities.SignupResponse;
import com.bankeasy.bankeasy.services.SignupService;

@RestController
@RequestMapping("/api/auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        System.out.println("Received signup request: " + signupRequest);
        SignupResponse response = signupService.signup(signupRequest);
        System.out.println("Signup response: " + response);
        if (response.isHasError()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

