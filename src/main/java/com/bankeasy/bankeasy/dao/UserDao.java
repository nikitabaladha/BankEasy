package com.bankeasy.bankeasy.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.User;

public interface UserDao extends JpaRepository<User, UUID> {
    User findByEmail(String email);
    Optional<User> findById(UUID id);
   
}