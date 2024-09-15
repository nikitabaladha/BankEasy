package com.bankeasy.bankeasy.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bankeasy.bankeasy.dao.UserDao;
import com.bankeasy.bankeasy.entities.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(UUID id) {
        return userDao.findById(id).orElse(null); 
    }
    
    @Override
    public void updateUserStatus(UUID userId, User.UserStatus status) {
        User user = userDao.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setStatus(status);
        userDao.save(user);
    }
    
    @Override
    public List<User> findByStatus(User.UserStatus status) {
        return userDao.findByStatus(status);
    }
    
    @Override
    public long countActiveUsers() {
        return userDao.countByStatus(User.UserStatus.Approved);
    }
    
    @Override
    public long countPendingUsers() {
        return userDao.countByStatus(User.UserStatus.Pending);
    }

    
    @Override
    public long countRejectedUsers() {
        return userDao.countByStatus(User.UserStatus.Rejected);
    }


}
