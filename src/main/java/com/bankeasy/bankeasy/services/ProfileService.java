package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

public interface ProfileService {
    Profile createProfile(User user, Account account, String name, String address, String phoneNumber);
 
}

