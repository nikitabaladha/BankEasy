package com.bankeasy.bankeasy.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bankeasy.bankeasy.entities.SignupRequest;
import com.bankeasy.bankeasy.entities.SignupResponse;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUser(long userId) {
        return userDao.findById(userId).orElse(null);
    }

    @Override
    public User addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateUser(long userId, User user) {
        user.setId(userId);
        return userDao.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        userDao.deleteById(userId);
    }

    @Override
    public SignupResponse signup(SignupRequest signupRequest) {
        if (userDao.findByEmail(signupRequest.getEmail()) != null) {
            return new SignupResponse(true, "User already exists");
        }
        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userDao.save(user);
        return new SignupResponse(false, "User registered successfully");
    }
}
