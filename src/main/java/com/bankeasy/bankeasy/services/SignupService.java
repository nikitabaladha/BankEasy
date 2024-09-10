package com.bankeasy.bankeasy.services;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.validators.SignupValidator;

public interface SignupService {
    ApiResponse<String> signup(SignupValidator signupValidator);
}