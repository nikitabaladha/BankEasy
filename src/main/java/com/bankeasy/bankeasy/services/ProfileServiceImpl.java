package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.ProfileDao;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;

    @Override
    public Profile createProfile(User user, Account account, String name, String address, String phoneNumber) {
        Profile profile = new Profile(user, account, name, address, phoneNumber);
        return profileDao.save(profile);
    }
}


