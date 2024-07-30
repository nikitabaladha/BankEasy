package com.bankeasy.bankeasy.services;

import java.util.List;

import java.util.UUID;

import com.bankeasy.bankeasy.reqres.SignupRequest;
import com.bankeasy.bankeasy.reqres.SignupResponse;
import com.bankeasy.bankeasy.entities.User;

public interface SignupService {
    public List<User> getUsers();
    public User getUser(UUID userId);
    public User addUser(User user);
    public User updateUser( UUID userId, User user);
    public void deleteUser(UUID userId);
    SignupResponse signup(SignupRequest signupRequest);
}
