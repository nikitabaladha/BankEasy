package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
