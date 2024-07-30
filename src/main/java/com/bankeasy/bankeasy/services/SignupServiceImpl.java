package com.bankeasy.bankeasy.services;

import java.security.SecureRandom;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.bankeasy.bankeasy.reqres.SignupRequest;
import com.bankeasy.bankeasy.reqres.SignupResponse;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.validators.SignupRequestValidator;
import com.bankeasy.bankeasy.dao.UserDao;

@Service
public class SignupServiceImpl implements SignupService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private SignupRequestValidator validator;

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUser(UUID userId) { // Changed to UUID
        return userDao.findById(userId).orElse(null);
    }

    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateUser(UUID userId, User user) { // Changed to UUID
        // Fetch existing user and update fields
        User existingUser = userDao.findById(userId).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword()); // Ensure password is updated correctly
            return userDao.save(existingUser);
        }
        return null;
    }

    @Override
    public void deleteUser(UUID userId) { // Changed to UUID
        userDao.deleteById(userId);
    }

    @Override
    public SignupResponse signup(SignupRequest signupRequest) {
        if (userDao.findByEmail(signupRequest.getEmail()) != null) {
            return new SignupResponse(true, "User already exists");
        }

        Errors errors = new BeanPropertyBindingResult(signupRequest, "signupRequest");
        validator.validate(signupRequest, errors);

        if (errors.hasErrors()) {
            String errorMessage = getErrorMessage(errors);
            return new SignupResponse(true, errorMessage);
        }

        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());

        String password = signupRequest.getPassword();
        String salt = generateSalt();
        user.setSalt(salt);
        user.setPassword(passwordEncoder.encode(password + salt));

        userDao.save(user);
        return new SignupResponse(false, "User registered successfully");
    }

    private String getErrorMessage(Errors errors) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError error : errors.getAllErrors()) {
            errorMessage.append(error.getDefaultMessage()).append(" ");
        }
        return errorMessage.toString().trim();
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return new String(Hex.encode(saltBytes));
    }
}
