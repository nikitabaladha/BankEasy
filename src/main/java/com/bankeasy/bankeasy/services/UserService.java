package com.bankeasy.bankeasy.services;

import java.util.List;

import com.bankeasy.bankeasy.entities.SignupRequest;
import com.bankeasy.bankeasy.entities.SignupResponse;
import com.bankeasy.bankeasy.entities.User;

public interface UserService {
    public List<User> getUsers();
    public User getUser(long userId);
    public User addUser(User user);
    public User updateUser(long userId, User user);
    public void deleteUser(long userId);
    SignupResponse signup(SignupRequest signupRequest);

}
