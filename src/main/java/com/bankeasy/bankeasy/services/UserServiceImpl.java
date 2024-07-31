package com.bankeasy.bankeasy.services;

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
}
