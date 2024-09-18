package com.bankeasy.bankeasy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankeasy.bankeasy.dao.UserDao;
import com.bankeasy.bankeasy.reqres.LoginRequest;
import com.bankeasy.bankeasy.reqres.LoginResponse;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.security.JwtService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            return new LoginResponse(true, "Email is required", null);
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            return new LoginResponse(true, "Password is required", null);
        }

        User user = userDao.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return new LoginResponse(true, "User not found", null);
        }

        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword() + user.getSalt(), user.getPassword());
        if (!isPasswordMatch) {
            return new LoginResponse(true, "Password does not match", null);
        }
        
        String token = jwtService.generateToken(user);
        return new LoginResponse(false, "Login successful", token);
    }
}