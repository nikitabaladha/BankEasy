
package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.LoginRequest;
import com.bankeasy.bankeasy.entities.LoginResponse;

public interface LoginService {
    LoginResponse login(LoginRequest loginRequest);
}
