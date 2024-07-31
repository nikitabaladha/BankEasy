package com.bankeasy.bankeasy.services;

import java.util.UUID;
import com.bankeasy.bankeasy.entities.User;

public interface UserService {
    User findByEmail(String email);
    User findById(UUID id);
	
}
