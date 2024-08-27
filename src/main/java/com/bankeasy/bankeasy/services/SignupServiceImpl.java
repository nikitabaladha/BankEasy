
package com.bankeasy.bankeasy.services;

import org.springframework.security.crypto.codec.Hex;

import com.bankeasy.bankeasy.dao.UserDao;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.validators.SignupValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class SignupServiceImpl implements SignupService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<String> signup(SignupValidator signupValidator) {
        // Validate the input data
        if (!isValid(signupValidator)) {
            return new ApiResponse<>(true, "Invalid input data", null);
        }

        // Check if email already exists
        User existingUser = userDao.findByEmail(signupValidator.getEmail());
        if (existingUser != null) {
            return new ApiResponse<>(true, "Email already exists", null);
        }

        // Create a new user
        User user = new User();
        user.setFirstName(signupValidator.getFirstName());
        user.setLastName(signupValidator.getLastName());
        user.setEmail(signupValidator.getEmail());

        String password = signupValidator.getPassword();
        String salt = generateSalt();
        user.setSalt(salt);
        user.setPassword(passwordEncoder.encode(password + salt));

        userDao.save(user);

        return new ApiResponse<>(false, "User registered successfully", null);
    }

    private boolean isValid(SignupValidator signupValidator) {
        // Add additional validation logic here if needed
        return true;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return new String(Hex.encode(saltBytes));
    }
}