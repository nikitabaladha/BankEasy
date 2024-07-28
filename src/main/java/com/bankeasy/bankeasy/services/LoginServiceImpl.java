//package com.bankeasy.bankeasy.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import com.bankeasy.bankeasy.entities.LoginRequest;
//import com.bankeasy.bankeasy.entities.LoginResponse;
//import com.bankeasy.bankeasy.entities.User;
//import com.bankeasy.bankeasy.dao.UserDao;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserDao userDao;
//
//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
//        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        User user = userDao.findByEmail(loginRequest.getEmail());
//        if (user!= null) {
//            String token = generateToken(user);
//            LoginResponse response = new LoginResponse(false, token, "Login successful");
//            return response;
//        } else {
//            LoginResponse response = new LoginResponse(true, null, "Invalid credentials");
//            return response;
//        }
//    }
//
//    private String generateToken(User user) {
//       
//        return "generated_token";
//    }
//}



//package com.bankeasy.bankeasy.services;
//
//import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import com.bankeasy.bankeasy.entities.LoginRequest;
//import com.bankeasy.bankeasy.entities.LoginResponse;
//import com.bankeasy.bankeasy.entities.User;
//import com.bankeasy.bankeasy.dao.UserDao;
//import com.bankeasy.bankeasy.utils.JwtUtil;
//
//@Service
//public class LoginServiceImpl implements LoginService {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        User user = userDao.findByEmail(loginRequest.getEmail());
//        if (user == null) {
//            return new LoginResponse(true, "User not found", null);
//        }
//
//        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword() + user.getSalt(), user.getPassword());
//        if (!isPasswordMatch) {
//            return new LoginResponse(true, "Invalid credentials", null);
//        }
//
//        String token = jwtUtil.generateToken(user);
//        return new LoginResponse(false, "Login successful", token);
//    }
//}



package com.bankeasy.bankeasy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bankeasy.bankeasy.dao.UserDao;
import com.bankeasy.bankeasy.entities.LoginRequest;
import com.bankeasy.bankeasy.entities.LoginResponse;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.utils.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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

        String token = jwtUtil.generateToken(user);
        return new LoginResponse(false, "Login successful", token);
    }
}

