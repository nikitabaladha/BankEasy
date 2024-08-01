package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.ProfileDao;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;
    
    public Profile createProfile(User user, String name, String address, String phoneNumber) {
        Profile profile = new Profile(user, name, address, phoneNumber);
        return profileDao.save(profile);
    }
}


