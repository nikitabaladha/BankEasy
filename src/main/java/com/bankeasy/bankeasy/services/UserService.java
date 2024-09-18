package com.bankeasy.bankeasy.services;

import java.util.List;
import java.util.UUID;

import com.bankeasy.bankeasy.entities.User;

public interface UserService {
    User findByEmail(String email);
    
    User findById(UUID id);
    
    void updateUserStatus(UUID userId, User.UserStatus status);
    
    List<User> findByStatus(User.UserStatus status);
    
    long countActiveUsers();
    
    long countPendingUsers();
    
    long countRejectedUsers();
    
    long countClosedUsers();
}
