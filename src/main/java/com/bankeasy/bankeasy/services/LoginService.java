package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.reqres.LoginRequest;
import com.bankeasy.bankeasy.reqres.LoginResponse;

public interface LoginService {
	
    LoginResponse login(LoginRequest loginRequest);
}