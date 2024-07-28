
package com.bankeasy.bankeasy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankeasy.bankeasy.dao.UserDao;
import com.bankeasy.bankeasy.entities.LoginRequest;
import com.bankeasy.bankeasy.entities.LoginResponse;
import com.bankeasy.bankeasy.entities.User;

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
        User user = userDao.findByEmail(loginRequest.getEmail());
        if (user == null) {
            return new LoginResponse(true, "User not found", null);
        }

        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword() + user.getSalt(), user.getPassword());
        if (!isPasswordMatch) {
            return new LoginResponse(true, "Invalid credentials", null);
        }
       String token = jwtService.GenerateToken(loginRequest.getEmail());
      

        return new LoginResponse(false, "Login successful", token);
    }
}